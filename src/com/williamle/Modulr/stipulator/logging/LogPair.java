package com.williamle.modulr.stipulator.logging;

import com.williamle.modulr.stipulator.models.LogSeverity;

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