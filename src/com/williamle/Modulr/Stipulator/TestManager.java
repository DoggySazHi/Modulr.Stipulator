package com.williamle.Modulr.Stipulator;

import com.williamle.Modulr.Stipulator.Logging.Logger;
import com.williamle.Modulr.Stipulator.Models.LogSeverity;
import com.williamle.Modulr.Stipulator.Models.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class TestManager {

    private final List<Test> loadedTests;
    private static final Class<org.junit.jupiter.api.Test> TEST_ANNOTATION = org.junit.jupiter.api.Test.class;

    public TestManager() {
        loadedTests = new LinkedList<>();
        fetchAllTests();
    }

    /**
     * This uses a ton of reflection "magic" to grab all classes from the classloader.
     * I couldn't find a good StackOverflow post (without libs), so I started digging through the API.
     * This apparently might work.
     */
    private void fetchAllTests() {
        var classLoader = Thread.currentThread().getContextClassLoader();
        try {
            var classes = classLoader.getResources("").asIterator();
            while (classes.hasNext()) {
                var url = classes.next();
                var classPath = new File(url.getPath());
                for (var c : classPath.listFiles())
                    Logger.log(LogSeverity.VERBOSE, c.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (classLoader != null) {
            loadMethods(classLoader);
            classLoader = classLoader.getParent();
        }
        Logger.log(LogSeverity.VERBOSE, "Found " + loadedTests.size() + " suites");
    }

    private static void loadMethods(ClassLoader classLoader) {
        // Force un-generify, to support loading.
        Class classLoaderClassObj = classLoader.getClass();
        while (classLoaderClassObj != ClassLoader.class)
            classLoaderClassObj = classLoaderClassObj.getSuperclass();
        try {
            Logger.log(LogSeverity.VERBOSE, "Attempting to check " + classLoaderClassObj.getName());
            var classField = classLoaderClassObj.getDeclaredField("classes");
            classField.setAccessible(true);
            var classes = (Vector<Class<?>>) classField.get(classLoader);
            for (var c : classes) {
                Logger.log(LogSeverity.VERBOSE, "Found " + classes.size() + " methods");
                var test = new Test(c.getName());
                // Get methods with attribute.
                Arrays.stream(c.getMethods())
                        .filter(o -> o.isAnnotationPresent(TEST_ANNOTATION))
                        .forEach(o -> test.registerTest(o.getName()));
            }
        } catch (Exception ex) {
            Logger.log(LogSeverity.VERBOSE, "Failed to reflect upon " + classLoaderClassObj.getName());
        }
    }
}
