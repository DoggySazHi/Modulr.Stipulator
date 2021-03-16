package com.williamle.Modulr.Stipulator.Logging;

import com.williamle.Modulr.Stipulator.Models.LogSeverity;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

public class Logger {
    private static final List<LogPair> log;
    private static final PrintStream output;

    static {
        log = new LinkedList<>();
        output = System.out;
        System.setOut(new TesterPrintStream());
    }

    public static void log(LogSeverity severity, String message) {

    }
}
