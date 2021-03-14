package com.williamle.Modulr.Stipulator.Models;

import java.util.HashMap;

// A class to represent *a class* and its testing methods.
public class Test {
    private String name;
    private HashMap<String, Results> data;

    private static class Results {
        private boolean success;
        private String message;

        public Results(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public Results(boolean success) {
            this(success, null);
        }

        public boolean isSuccessful() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }
}
