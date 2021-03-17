package com.williamle.Modulr.Stipulator;

public class Startup {
    private static final String version = "1.0.0";
    private static TestManager tm;

    public static void main(String[] args) {
        System.out.println("Running Modulr.Stipulator " + version);
        initialize();
        executeTests();
        cleanUp();
    }

    private static void initialize() {
        tm = new TestManager();
    }

    private static void executeTests() {
        tm.execute();
    }

    private static void cleanUp() {

    }
}
