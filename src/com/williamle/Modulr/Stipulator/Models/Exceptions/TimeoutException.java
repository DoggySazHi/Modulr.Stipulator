package com.williamle.Modulr.Stipulator.Models.Exceptions;

public class TimeoutException extends TestFailureException {
    public TimeoutException(long time, String message) {
        super(
                (message != null ? message + "\n - " : "") +
                "Execution timed out after " + time + "ms"
        );
    }

    public TimeoutException(long time) {
        this(time, null);
    }
}
