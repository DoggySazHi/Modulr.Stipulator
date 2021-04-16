package com.williamle.modulr.stipulator;

import com.williamle.modulr.stipulator.logging.Logger;
import com.williamle.modulr.stipulator.logging.TestManagerPrinter;
import com.williamle.modulr.stipulator.models.LogSeverity;

/**
 * The main class to run the entire Stipulator.
 */
public class Startup {
    private static TestManager tm;

    /**
     * The starting point of the program. Initializes settings, runs tests, and executes closing tasks.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        Logger.log(LogSeverity.INFO, "Running Modulr.Stipulator " + Settings.VERSION);
        Settings.loadFromCommandLine(args);
        initialize();
        executeTests();
        cleanUp();
    }

    /**
     * The initialization of the tester.
     * This usually involves setting up the <code>TestManager</code> and <code>Security</code>.
     */
    private static void initialize() {
        tm = new TestManager();
        Security.enableSecurity(Settings.AllowRW);
    }

    /**
     * The main part of the tester.
     * This runs the <code>TestManager</code> and prints output using a <code>TestManagerPrinter</code>.
     */
    private static void executeTests() {
        var printer = new TestManagerPrinter(tm);
        if (Settings.WriteInRealTime)
            printer.enableLivePrint();
        tm.execute();
        if (!Settings.WriteInRealTime)
            printer.print();
    }

    /**
     * The clean-up of the tester.
     * Here, all log files are created, if demanded for by the user.
     */
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
