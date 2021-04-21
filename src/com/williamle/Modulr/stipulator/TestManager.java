package com.williamle.modulr.stipulator;

import com.williamle.modulr.stipulator.logging.Logger;
import com.williamle.modulr.stipulator.models.exceptions.BadTesterException;
import com.williamle.modulr.stipulator.models.LogSeverity;
import com.williamle.modulr.stipulator.models.Test;
import com.williamle.modulr.stipulator.models.exceptions.TestFailureException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * The heart of Modulr.Stipulator, all tests are reflected and executed here.
 */
public class TestManager {

    private final ClassLoader loader;
    private final List<Test> loadedTests;

    private Consumer<TestManager> onStartAll;
    private Consumer<Test> onStartSuite;
    private Consumer<Method> onStartTest;
    private Consumer<Method> onEndTest;
    private Consumer<Test> onEndSuite;
    private Consumer<TestManager> onEndAll;

    private static final Class<org.junit.jupiter.api.Test> TEST_ANNOTATION = org.junit.jupiter.api.Test.class;
    private static final Class<org.junit.Test> OLD_TEST_ANNOTATION = org.junit.Test.class;

    public TestManager() {
        this(Thread.currentThread().getContextClassLoader());
        fetchAllTests();
    }

    public TestManager(ClassLoader loader) {
        this.loader = loader;
        loadedTests = new LinkedList<>();
    }

    private static final boolean IS_WINDOWS = System.getProperty("os.name").toLowerCase().contains("win");

    /**
     * A method intended to load all classes, detect their methods, and filter out which ones have tests. They will be stored.
     *
     * This uses a ton of reflection "magic" to grab all classes from the classloader.
     * I couldn't find a good StackOverflow post (without libs), so I started digging through the API.
     * This apparently might work.
     */
    private void fetchAllTests() {
        // Shouldn't use classLoader.getDefinedPackages(), as it can include debugger classes loaded.
        var allClassFiles = new ArrayList<String>();
        var classLoader = loader;
        Logger.log(LogSeverity.VERBOSE, "Looking at loader " + classLoader.getName());
        Logger.log(LogSeverity.VERBOSE, System.getProperty("java.class.path"));
        try {
            var classes = classLoader.getResources("").asIterator();
            while (classes.hasNext()) { // Fetch all class paths, then convert the directories to package name. We hope it's correct.
                var classFiles = new ArrayList<String>();

                var url = classes.next();
                var path = URLDecoder.decode(url.getPath(), StandardCharsets.UTF_8);
                if (IS_WINDOWS) {
                    path = path.replaceAll("/([A-Z]):", "$1:").replace("/", "\\");
                }
                Logger.log(LogSeverity.VERBOSE, "Classpath loading at " + path);
                var classPath = new File(path);
                searchPath(classPath, classFiles);

                String finalPath = path;
                classFiles.replaceAll(o -> {
                    var temp = o.replace(finalPath, "");
                    if (IS_WINDOWS)
                        return temp.replace("\\", ".");
                    return temp.replace("/", ".");
                });
                allClassFiles.addAll(classFiles);
            }
        } catch (IOException e) {
            // ignored
        }

        while (classLoader != null) {
            loadMethods(classLoader, allClassFiles);
            classLoader = classLoader.getParent();
        }
        var size = loadedTests.size();
        Logger.log(LogSeverity.INFO, "Found " + size + " suite" + (size == 1 ? "" : "s"));
    }

    /**
     * Recursively traverses a direction, searching for class files.
     * @param f The current position in the directory traversal.
     * @param files An output reference to put all class/jar files in.
     */
    private void searchPath(File f, List<String> files) {
        if (f.isDirectory()) {
            var directoryListing = f.listFiles();
            if (directoryListing != null) {
                for (var c : directoryListing)
                    searchPath(c, files);
            }
        }
        else {
            var fileName = f.getName();
            if (fileName.endsWith("class") || fileName.endsWith("jar"))
                files.add(f.getAbsolutePath());
        }
    }

    /**
     * Find all classes in the classloader using a list of paths.
     * @param classLoader A ClassLoader to find classes from, usually the default instance.
     * @param toLoad Paths to individual classes, usually from the classpath found in <code>searchPath(File, List<String>)</code>.
     */
    private void loadMethods(ClassLoader classLoader, List<String> toLoad) {
        // Force un-generify, to support loading.
        for (var c : toLoad) {
            try {
                var classObj = classLoader.loadClass(c.replace(".class", ""));
                // Get methods with attribute.
                var testMethods = Arrays.stream(classObj.getMethods())
                        .filter(o -> {
                            if (o.isAnnotationPresent(TEST_ANNOTATION) || o.isAnnotationPresent(OLD_TEST_ANNOTATION))
                                return true;
                            return classObj.getSuperclass().equals(junit.framework.TestCase.class) && o.getName().startsWith("test");
                        })
                        .collect(Collectors.toList());
                if (testMethods.size() > 0) {
                    var test = new Test(classObj.getName(), classObj);
                    for (var testMethod : testMethods) {
                        test.registerTest(testMethod);
                    }
                    loadedTests.add(test);
                }
            } catch (Exception ex) {
                // ignored, probably not in the classloader
            }
        }
    }

    public void execute() {
        triggerCallback(onStartAll, this);
        for (var testSuite : loadedTests) {
            var instance = getInstance(testSuite);

            triggerCallback(onStartSuite, testSuite);
            for(var method : testSuite.getTestRelation().keySet()) {
                triggerCallback(onStartTest, method);
                var start = System.nanoTime();
                try {
                    if (Modifier.isStatic(method.getModifiers()))
                        method.invoke(null);
                    else if (instance == null)
                        throw new BadTesterException("No suitable empty constructor found for non-static method");
                    method.invoke(instance);
                } catch (Throwable ex) {
                    if (ex instanceof InvocationTargetException) {
                        var trueEx = (InvocationTargetException) ex;
                        ex = trueEx.getCause();
                    }

                    testSuite.setResults(
                            method,
                            new Test.Result(
                                    false,
                                    getMessage(ex),
                                    System.nanoTime() - start,
                                    Logger.getSystemRedirect().getAndFlush()
                            )
                    );
                    triggerCallback(onEndTest, method);
                    continue;
                }
                testSuite.setResults(method, new Test.Result(true, System.nanoTime() - start));
                triggerCallback(onEndTest, method);
            }
            triggerCallback(onEndSuite, testSuite);
        }
        triggerCallback(onEndAll, this);
    }

    /**
     * Find a good constructor to build the test suite from, then create an instance.
     * @param testSuite The test suite to create an instance for.
     * @throws BadTesterException If the class cannot be instantiated, either via missing constructors or internally-thrown exceptions.
     * @return An object representing the test suite.
     */
    private Object getInstance(Test testSuite) {
        var start = System.nanoTime();
        try {
            //TODO An attribute to annotate constructors that Modulr.Stipulator should use?
            // Sort by accessibility (mainly to avoid trying to call private constructors)
            var constructor = Arrays.stream(testSuite.getTester().getDeclaredConstructors())
                    .filter(o -> o.getParameterCount() == 0)
                    .min(Comparator.comparingInt(a -> {
                        if (Modifier.isPublic(a.getModifiers()))
                            return 3;
                        if (Modifier.isProtected(a.getModifiers()))
                            return 2;
                        return 1;
                    }))
                    .orElseThrow(() -> new BadTesterException(testSuite.getClass(), "No suitable empty constructor found"));
            return constructor.newInstance();
        } catch (Throwable ex) {
            if (ex instanceof InvocationTargetException) {
                var trueEx = (InvocationTargetException) ex;
                ex = trueEx.getCause();
            }

            testSuite.setAllResults(
                    new Test.Result(
                            false,
                            getMessage(ex),
                            System.nanoTime() - start,
                            Logger.getSystemRedirect().getAndFlush()
                    )
            ); // Can't instantiate it. Might be a utility class.
        }
        return null;
    }

    private String getMessage(Throwable ex) {
        if (ex instanceof TestFailureException) {
            return ex.getClass().getSimpleName() +
                    (ex.getLocalizedMessage() != null ? ": " + ex.getLocalizedMessage() : "");
        }
        return ex.toString();
    }

    /**
     * Get a list reference of all tests held loaded by this <code>TestManager</code>.
     * @return A read-only list pointing to the internal list.
     */
    public List<Test> getLoadedTests() {
        return Collections.unmodifiableList(loadedTests);
    }

    public void setOnAllStart(Consumer<TestManager> callback) {
        onStartAll = callback;
    }

    public void setOnSuiteStart(Consumer<Test> callback) {
        onStartSuite = callback;
    }

    public void setOnTestStart(Consumer<Method> callback) {
        onStartTest = callback;
    }

    public void setOnTestEnd(Consumer<Method> callback) {
        onEndTest = callback;
    }

    public void setOnSuiteEnd(Consumer<Test> callback) {
        onEndSuite = callback;
    }

    public void setOnAllEnd(Consumer<TestManager> callback) {
        onEndAll = callback;
    }

    private <T> void triggerCallback(Consumer<T> callback, T obj) {
        if (callback == null)
            return;
        try {
            callback.accept(obj);
        } catch (Throwable thrown){
            Logger.log(LogSeverity.ERROR, "Callback " + callback + " threw an exception! Disabling.");
        }
    }
}
