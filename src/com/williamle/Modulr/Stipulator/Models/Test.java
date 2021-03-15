package com.williamle.Modulr.Stipulator.Models;

import java.util.HashMap;
import java.util.Map;

// A class to represent a test suite and its individual tests.
public class Test {
    private final String name;
    private final Map<String, Results> data;

    public Test(String name) {
        this.name = name;
        data = new HashMap<>();
    }

    private static class Results {
        private final boolean success;
        private final String message;
        private final long time;

        public Results(boolean success, String message, long time) {
            this.success = success;
            this.message = message;
            this.time = time;
        }

        public Results(boolean success, String message) {
            this(success, message, 0);
        }

        public Results(boolean success) {
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
}
