package com.williamle.modulr.stipulator.logging;

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

    public TestManagerPrinter(TestManager tm) {
        this.tm = tm;
    }

    public void enableLivePrint() {
        tm.setOnSuiteStart(this::onSuiteStart);
        tm.setOnTestStart(this::onTestStart);
        tm.setOnTestEnd(this::onTestEnd);
        tm.setOnSuiteEnd(this::onSuiteEnd);
        Logger.log(LogSeverity.VERBOSE, "Hooked TestManager callbacks.");
    }

    public void disableLivePrint() {
        tm.setOnSuiteStart(null);
        tm.setOnTestStart(null);
        tm.setOnTestEnd(null);
        tm.setOnSuiteEnd(null);
        Logger.log(LogSeverity.VERBOSE, "Removed TestManager callbacks.");
    }

    private void onSuiteStart(Test t) {
        currentTest = t;
        Logger.log(LogSeverity.INFO, "[" + t.getName() + "]");
    }

    private void onTestStart(Method m) {
        testSubstring = "- " + m.getName() + "() ...";
        Logger.mainOutput.print(testSubstring);
    }

    private void onTestEnd(Method m) {
        var result = currentTest.getTestRelation().get(m);
        var time = result.getTime() >= 0 ? (result.getTime() / 1000000L) + "ms" : "";
        var next = result.isSuccessful() ? " [PASS " + time + "]" : " [FAIL " + time + "]";
        if (result.getMessage() != null)
            next += "\n  - " + result.getMessage();
        Logger.log.add(new LogPair(LogSeverity.INFO, testSubstring + next));
    }

    private void onSuiteEnd(Test t) {

    }

    /**
     * Prints all test data currently in the <code>TestManager</code>.
     */
    public void print() {
        long totalTime = 0;
        int suitesPassed = 0;
        int suitesFailed = 0;
        int testsPassed = 0;
        int testsFailed = 0;

        for (var suite : tm.getLoadedTests()) {
            Logger.log(LogSeverity.INFO, "[" + suite.getName() + "]");
            var suitePassed = true;
            for (var test : suite.getTestRelation().entrySet()) {
                var method = test.getKey();
                var result = test.getValue();

                var time = "";

                var message = "- " + method.getName();

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
                if (result.getMessage() != null)
                    message += "\n  - " + result.getMessage();
                Logger.log(LogSeverity.INFO, message);
            }
            if (suitePassed)
                ++suitesPassed;
            else
                ++suitesFailed;
        }

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
}
