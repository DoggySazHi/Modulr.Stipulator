package com.williamle.modulr.stipulator.logging;

import com.williamle.modulr.stipulator.models.LogSeverity;

/**
 * A structure class which represents the severity of the message and the message itself.
 */
public class LogPair {
    private final LogSeverity logSeverity;
    private final String message;

    /**
     * Create a <code>LogPair</code> based on the exact two parameters.
     * @param logSeverity The importance of the message.
     * @param message A string containing the message.
     */
    public LogPair(LogSeverity logSeverity, String message) {
        this.logSeverity = logSeverity;
        this.message = message;
    }

    /**
     * Get the log severity of this pair.
     * @return The log severity.
     */
    public LogSeverity getLogSeverity() {
        return logSeverity;
    }

    /**
     * Get the message of this pair.
     * @return The message.
     */
    public String getMessage() {
        return message;
    }
}