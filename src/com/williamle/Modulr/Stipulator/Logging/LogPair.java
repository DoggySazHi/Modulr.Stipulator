package com.williamle.Modulr.Stipulator.Logging;

import com.williamle.Modulr.Stipulator.Models.LogSeverity;

public class LogPair {
    private final LogSeverity logSeverity;
    private final String message;

    public LogPair(LogSeverity logSeverity, String message) {
        this.logSeverity = logSeverity;
        this.message = message;
    }

    public LogSeverity getLogSeverity() {
        return logSeverity;
    }

    public String getMessage() {
        return message;
    }
}