package com.williamle.Modulr.Stipulator.Models;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// A class to represent a test suite and its individual tests.
public class Test {
    private final String name;
    private final Class<?> tester;
    private final Map<Method, Result> data;

    public Test(String name, Class<?> tester) {
        this.name = name;
        this.tester = tester;
        data = new HashMap<>();
    }

    public static class Result {
        private final boolean success;
        private final String message;
        private final long time;

        public Result(boolean success, String message, long time) {
            this.success = success;
            this.message = message;
            this.time = time;
        }

        public Result(boolean success, String message) {
            this(success, message, 0);
        }

        public Result(boolean success, long time) {
            this(success, null, 0);
        }

        public Result(boolean success) {
            this(success, null);
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
         * @return The amount of time that the test method took to execute. May be 0, if unimplemented.
         */
        public long getTime() {
            return time;
        }
    }

    public String getName() {
        return name;
    }

    public Class<?> getTester() {
        return tester;
    }

    public boolean registerTest(Method method) {
        if (data.containsKey(method))
            return false;
        data.putIfAbsent(method, null);
        return true;
    }

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
}
