package com.williamle.Modulr.Stipulator.Models.Exceptions;

public class TimeoutException extends TestFailureException {
    /**
     * Create a new <code>TimeoutException</code> with all possible parameters.
     * @param time The time limit.
     * @param realTime The time actually taken. If negative, it will be ignored.
     * @param message A custom message associated with this <code>TimeoutException</code>. Can be <code>null</code>.
     */
    public TimeoutException(long time, long realTime, String message) {
        super(
                (message != null ? message + "\n - " : "") +
                "Execution timed out after " + time + "ms" +
                (realTime >= 0 ? " (" + realTime + "ms)" : "")
        );
    }

    /**
     * Create a new <code>TimeoutException</code> with a custom message.
     * @param time The time limit.
     * @param message A custom message associated with this <code>TimeoutException</code>.
     */
    public TimeoutException(long time, String message) {
        this(time, -1, message);
    }

    /**
     * Create a new <code>TimeoutException</code> with a custom message.
     * @param time The time limit.
     * @param realTime The actual time taken to execute.
     */
    public TimeoutException(long time, long realTime) {
        this(time, realTime, null);
    }

    /**
     * Create a new <code>TimeoutException</code> with a custom message.
     * @param time The time limit.
     */
    public TimeoutException(long time) {
        this(time, -1, null);
    }
}
