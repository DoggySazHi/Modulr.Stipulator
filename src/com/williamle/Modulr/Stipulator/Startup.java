package com.williamle.Modulr.Stipulator;

import com.williamle.Modulr.Stipulator.Logging.Logger;
import com.williamle.Modulr.Stipulator.Models.LogSeverity;

public class Startup {
    private static final String version = "1.0.7";
    private static TestManager tm;

    public static void main(String[] args) {
        Logger.log(LogSeverity.INFO, "Running Modulr.Stipulator " + version);
        Settings.loadFromCommandLine(args);
        initialize();
        executeTests();
        cleanUp();
    }

    private static void initialize() {
        tm = new TestManager();
        Security.enableSecurity(Settings.AllowRW);
    }

    private static void executeTests() {
        tm.execute();
    }

    private static void cleanUp() {

    }
}
