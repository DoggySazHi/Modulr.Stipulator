package com.williamle.modulr.stipulator;

import com.williamle.modulr.stipulator.logging.Logger;
import com.williamle.modulr.stipulator.logging.TestManagerPrinter;
import com.williamle.modulr.stipulator.models.LogSeverity;

public class Startup {
    private static final String version = "1.0.12";
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
        var printer = new TestManagerPrinter(tm);
        if (Settings.WriteInRealTime)
            printer.enableLivePrint();
        tm.execute();
        if (!Settings.WriteInRealTime)
            printer.print();
    }

    private static void cleanUp() {
        if (Settings.WriteLogOnExit) {
            try {
                var output = Logger.dumpLogToFile("log.txt");
                Logger.log(LogSeverity.INFO, "Saved logs to " + output);
            } catch (Throwable ex) {
                Logger.log(LogSeverity.ERROR, "Failed to dump logs!\n" + ex);
            }
        }
    }
}
