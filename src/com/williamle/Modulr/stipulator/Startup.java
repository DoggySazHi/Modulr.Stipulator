package com.williamle.modulr.stipulator;

import com.williamle.modulr.stipulator.logging.Logger;
import com.williamle.modulr.stipulator.logging.TestManagerPrinter;
import com.williamle.modulr.stipulator.models.LogSeverity;

import java.io.IOException;

public class Startup {
    private static final String version = "1.0.10";
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
                Logger.dumpLogToFile("log.txt");
            } catch (IOException e) {
                Logger.log(LogSeverity.ERROR, "Failed to dump logs!\n" + e);
            }
        }
    }
}
