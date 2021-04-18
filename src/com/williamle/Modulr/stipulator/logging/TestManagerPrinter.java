package com.williamle.modulr.stipulator.logging;

import com.williamle.modulr.stipulator.Settings;
import com.williamle.modulr.stipulator.TestManager;
import com.williamle.modulr.stipulator.models.LogSeverity;
import com.williamle.modulr.stipulator.models.Test;

import java.lang.reflect.Method;
import java.text.DecimalFormat;

/**
 * A utility class simply used to nicely format the <code>TestManager</code>'s output.
 */
public class TestManagerPrinter {
    private final TestManager tm;
    private Test currentTest;
    private String testSubstring;
    private static final DecimalFormat SCORE_FORMAT = new DecimalFormat("0.00");

    private long totalTime = 0;
    private int suitesPassed = 0;
    private int suitesFailed = 0;
    private boolean suitePassed = true;
    private int testsPassed = 0;
    private int testsFailed = 0;

    public TestManagerPrinter(TestManager tm) {
        this.tm = tm;
    }

    /**
     * Log all outputs as they are executed in the tester.
     * Note that this method does not execute tests by default.
     */
    public void enableLivePrint() {
        tm.setOnAllStart(this::onAllStart);
        tm.setOnSuiteStart(this::onSuiteStart);
        tm.setOnTestStart(this::onTestStart);
        tm.setOnTestEnd(this::onTestEnd);
        tm.setOnSuiteEnd(this::onSuiteEnd);
        tm.setOnAllEnd(this::onAllEnd);
        Logger.log(LogSeverity.VERBOSE, "Hooked TestManager callbacks.");
    }

    /**
     * Stop logging outputs.
     */
    public void disableLivePrint() {
        tm.setOnAllStart(null);
        tm.setOnSuiteStart(null);
        tm.setOnTestStart(null);
        tm.setOnTestEnd(null);
        tm.setOnSuiteEnd(null);
        tm.setOnAllEnd(null);
        Logger.log(LogSeverity.VERBOSE, "Removed TestManager callbacks.");
    }

    /**
     * Reset all internal parameters.
     * @param t The test manager, triggered by the callback.
     */
    private void onAllStart(TestManager t) {
        totalTime = 0;
        suitesPassed = 0;
        suitesFailed = 0;
        testsPassed = 0;
        testsFailed = 0;
    }

    /**
     * Print the current name of the suite.
     * @param t The test, triggered by the callback.
     */
    private void onSuiteStart(Test t) {
        currentTest = t;
        Logger.log(LogSeverity.INFO, "[" + t.getName() + "]");
    }

    /**
     * Print the current test name.
     * @param m The method, triggered by the callback.
     */
    private void onTestStart(Method m) {
        testSubstring = "- " + m.getName() + "() ...";
        suitePassed = true;
        Logger.mainOutput.print(testSubstring);
    }

    /**
     * Print the results of the current test, filling in the blanks of the previous callbacks.
     * @param method The method, triggered by the callback.
     */
    private void onTestEnd(Method method) {
        var result = currentTest.getTestRelation().get(method);

        var time = "";
        var message = "";

        if (result.getTime() >= 0) {
            time = (result.getTime() / 1000000L) + "ms";
            totalTime += result.getTime();
        }

        if (result.isSuccessful()) {
            ++testsPassed;
            message += " [PASS " + time + "]";
        } else {
            ++testsFailed;
            suitePassed = false;
            message += " [FAIL " + time + "]";
        }
        if (result.getMessage() != null && Settings.PrintFailMessages)
            message += "\n  - " + result.getMessage();
        Logger.log.add(new LogPair(LogSeverity.INFO, testSubstring + message));
        Logger.mainOutput.println(message);
    }

    /**
     * Record whether the suite passed or not.
     * @param t The test, triggered by the callback.
     */
    private void onSuiteEnd(Test t) {
        if (suitePassed)
            ++suitesPassed;
        else
            ++suitesFailed;
    }

    /**
     * Calculate test statistics for all test suites in the <code>TestManager</code>
     * @param t The test manager, triggered by the callback.
     */
    private void onAllEnd(TestManager t) {
        var totalSuites = suitesPassed + suitesFailed;
        var totalTests = testsPassed + testsFailed;

        Logger.log(LogSeverity.INFO, totalTests + " test" + (totalTests == 1 ? "" : "s") +
                " in " + totalSuites + " suite" + (totalSuites == 1 ? "" : "s") +
                " finished in " + (totalTime / 1000000L) + "ms");
        Logger.log(LogSeverity.INFO, "- " + testsPassed + " test" + (testsPassed == 1 ? "" : "s") + " passed");
        Logger.log(LogSeverity.INFO, "- " + testsFailed + " test" + (testsFailed == 1 ? "" : "s") + " failed");
        Logger.log(LogSeverity.INFO, "- " + suitesPassed + " suite" + (suitesPassed == 1 ? "" : "s") + " passed");
        Logger.log(LogSeverity.INFO, "- " + suitesFailed + " suite" + (suitesFailed == 1 ? "" : "s") + " failed");
        Logger.log(LogSeverity.INFO, "");

        var score = (double) testsPassed / (double) totalTests * 100.0;
        if (totalTests == 0)
            score = 0;
        Logger.log(testsFailed == 0 ? LogSeverity.INFO : LogSeverity.WARNING, "Score: " + SCORE_FORMAT.format(score) + "%");
    }

    /**
     * Prints all test data currently in the <code>TestManager</code>.
     * Note that this method does not execute tests by default.
     */
    public void print() {
        onAllStart(tm);

        for (var suite : tm.getLoadedTests()) {
            currentTest = suite;
            onSuiteStart(currentTest);
            for (var test : suite.getTestRelation().keySet()) {
                onTestStart(test);
                onTestEnd(test);
            }
            onSuiteEnd(currentTest);
        }

        onAllEnd(tm);
    }
}
