package com.williamle.modulr.stipulator.models.exceptions;

public class AssertionFailedException extends TestFailureException {

    /**
     * Generate an <code>AssertionFailedException</code> with a fully custom message.
     * @param message A custom message for the exception.
     */
    public AssertionFailedException(String message) {
        super(message);
    }

    /**
     * Generate an <code>AssertionFailedException</code> with the two values.
     * @param expected The value expected.
     * @param actual The actual value that we got.
     * @param message A custom message for the exception.
     */
    public AssertionFailedException(String expected, String actual, String message) {
        this((message != null ? message + "\n  - " : "") + "Expected [" + expected + "] but was [" + actual + "]");
    }

    /**
     * Generate an <code>AssertionFailedException</code> with the two values.
     * @param expected The value expected.
     * @param actual The actual value that we got.
     */
    public AssertionFailedException(String expected, String actual) {
        this(expected, actual, null);
    }
}
