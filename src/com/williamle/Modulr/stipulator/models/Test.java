package com.williamle.modulr.stipulator.models;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// A class to represent a test suite and its individual tests.
public class Test {
    private final String name;
    private final Class<?> tester;
    private final Map<Method, Result> data;
    private final Method setUp;
    private final Method tearDown;

    public Test(String name, Class<?> tester, Method setUp, Method tearDown) {
        this.name = name;
        this.tester = tester;
        data = new HashMap<>();
        this.setUp = setUp;
        this.tearDown = tearDown;
    }

    public Test(String name, Class<?> tester) {
        this(name, tester, null, null);
    }

    public static class Result {
        private final boolean success;
        private final String message;
        private final long time;
        private final String output;

        public Result(boolean success, String message, long time, String output) {
            this.success = success;
            this.message = message;
            this.time = time;
            this.output = output;
        }

        public Result(boolean success, String message) {
            this(success, message, 0, null);
        }

        public Result(boolean success, long time) {
            this(success, null, 0, null);
        }

        /**
         * A result that shows success or failure.
         * @param success Whether the test was successful or not.
         */
        public Result(boolean success) {
            this(success, null, 0, null);
        }

        /**
         * @return Whether the test executed successfully or not.
         */
        public boolean isSuccessful() {
            return success;
        }

        /**
         * @return Get the message associated with the result, if any. May be null.
         */
        public String getMessage() {
            return message;
        }

        /**
         * A generic time value. Usually in nanoseconds, implementation is not defined.
         * @return The amount of time that the test method took to execute. May be 0, if unimplemented.
         */
        public long getTime() {
            return time;
        }

        /**
         * Get all logged output created from <code>System.out</code> and <code>System.err</code>.
         * @return Output created by the tested method.
         */
        public String getOutput() {
            return output;
        }
    }

    /**
     * Get the name of the current test suite.
     * @return The name of the current test suite.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the current test suite class.
     * @return A class object, representing the test suite.
     */
    public Class<?> getTester() {
        return tester;
    }

    /**
     * Allocate a slot for a given method.
     * @param method The method found in the test suite.
     */
    public void registerTest(Method method) {
        data.putIfAbsent(method, null);
    }

    /**
     * Store the test results after running the specific method.
     * @param method The method found in the test suite.
     * @param result Output data that was recorded after executing the method.
     */
    public void setResults(Method method, Result result) {
        data.put(method, result);
    }

    /**
     * Only known for failing an entire class.
     * @param result The result assigned to all testers.
     */
    public void setAllResults(Result result) {
        data.replaceAll((k, v) -> result);
    }

    /**
     * Get all methods and results associated with this test.
     * @return An unmodifiable map containing methods and results.
     */
    public Map<Method, Result> getTestRelation() {
        return Collections.unmodifiableMap(data);
    }

    /**
     * Call the JUnit <code>setUp</code> method.
     * @param o An object, if it exists.
     */
    public void setUp(Object o) {
        if (setUp == null)
            return;
        try {
            setUp.invoke(o);
        } catch (Throwable t) {
            // Leave that up to the TestManager.
            throw new RuntimeException(t);
        }
    }

    /**
     * Call the JUnit <code>tearDown</code> method.
     * @param o An object, if it exists.
     */
    public void tearDown(Object o) {
        if (tearDown == null)
            return;
        try {
            tearDown.invoke(o);
        } catch (Throwable t) {
            // Leave that up to the TestManager.
            throw new RuntimeException(t);
        }
    }
}
