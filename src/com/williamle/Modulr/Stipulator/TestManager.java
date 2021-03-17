package com.williamle.Modulr.Stipulator;

import com.williamle.Modulr.Stipulator.Logging.Logger;
import com.williamle.Modulr.Stipulator.Models.Exceptions.BadTesterException;
import com.williamle.Modulr.Stipulator.Models.LogSeverity;
import com.williamle.Modulr.Stipulator.Models.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class TestManager {

    private final ClassLoader loader;
    private final List<Test> loadedTests;
    private static final Class<org.junit.jupiter.api.Test> TEST_ANNOTATION = org.junit.jupiter.api.Test.class;

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
        try {
            var classes = classLoader.getResources("").asIterator();
            while (classes.hasNext()) { // Fetch all class paths, then convert the directories to package name. We hope it's correct.
                var classFiles = new ArrayList<String>();

                var url = classes.next();
                var path = URLDecoder.decode(url.getPath(), StandardCharsets.UTF_8);
                if (IS_WINDOWS) {
                    path = path.replaceAll("/([A-Z]):", "$1:").replace("/", "\\");
                }
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
                        .filter(o -> o.isAnnotationPresent(TEST_ANNOTATION)).collect(Collectors.toList());
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
        for (var testSuite : loadedTests) {
            var start = System.nanoTime();
            Object instance = null;
            try {
                var constructor = Arrays.stream(testSuite.getTester().getDeclaredConstructors())
                        .filter(o -> o.getParameterCount() == 0)
                        .sorted(Comparator.comparingInt(a -> {
                            if (Modifier.isPublic(a.getModifiers()))
                                return 3;
                            if (Modifier.isProtected(a.getModifiers()))
                                return 2;
                            return 1;
                        })) // Sort by accessibility (mainly to avoid trying to call private constructors)
                        .findFirst()
                        .orElseThrow(() -> new BadTesterException(testSuite.getClass(), "No suitable empty constructor found"));
                instance = constructor.newInstance();
            } catch (Throwable ex) {
                testSuite.setAllResults(
                        new Test.Result(
                                false,
                                ex.toString(),
                                System.nanoTime() - start
                        )
                ); // Can't instantiate it. Might be a utility class.
            }

            for(var method : testSuite.getTestRelation().keySet()) {
                start = System.nanoTime();
                try {
                    if (Modifier.isStatic(method.getModifiers()))
                        method.invoke(null);
                    else
                        method.invoke(instance);
                } catch (Throwable ex) {
                    testSuite.setResults(
                            method,
                            new Test.Result(
                                    false,
                                    ex.toString(),
                                    System.nanoTime() - start
                            )
                    );
                    continue;
                }
                testSuite.setResults(method, new Test.Result(true, System.nanoTime() - start));
            }
        }
    }
}
