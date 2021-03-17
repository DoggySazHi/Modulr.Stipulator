package com.williamle.Modulr.Stipulator.Logging;

import com.williamle.Modulr.Stipulator.Models.LogSeverity;
import com.williamle.Modulr.Stipulator.Settings;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

public class Logger {
    private static final List<LogPair> log;
    private static final PrintStream output;
    private static final TesterPrintStream redirectOutput;

    static {
        log = new LinkedList<>();
        output = System.out;
        redirectOutput = new TesterPrintStream();
        System.setOut(redirectOutput);
    }

    public static void log(LogSeverity severity, String message) {
        log.add(new LogPair(severity, message));
        if (severity.ordinal() <= Settings.LogLevel.ordinal())
            output.println(message);
    }

    public static TesterPrintStream getSystemRedirect() {
        return redirectOutput;
    }
}
