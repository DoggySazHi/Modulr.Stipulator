package com.williamle.Modulr.Stipulator;

import com.williamle.Modulr.Stipulator.Models.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class TestManager {

    private final List<Test> loadedTests;
    private final Class<org.junit.jupiter.api.Test> TEST_ANNOTATION = org.junit.jupiter.api.Test.class;

    public TestManager() {
        loadedTests = new LinkedList<>();
    }

    /**
     * This uses a ton of reflection "magic" to grab all classes from the classloader.
     * I couldn't find a good StackOverflow post (without libs), so I started digging through the API.
     * This apparently might work.
     */
    private void fetchAllTests() {
        var classLoader = Thread.currentThread().getContextClassLoader();

        while (classLoader != null) {

            classLoader = classLoader.getParent();
        }
    }

    private static void loadMethods(ClassLoader classLoader) throws IllegalAccessException, NoSuchFieldException {
        // Force un-generify, to support loading.
        Class classLoaderClassObj = classLoader.getClass();
        while (classLoaderClassObj != ClassLoader.class)
            classLoaderClassObj = classLoaderClassObj.getSuperclass();
        var classField = classLoaderClassObj.getDeclaredField("classes");
        classField.setAccessible(true);
        var classes = (Vector<Class<?>>) classField.get(classLoader);
        for(var c : classes) {
            // Get methods with attribute.
        }
    }
}
