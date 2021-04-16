package com.williamle.modulr.stipulator.models.exceptions;

public class AssertionFailedException extends TestFailureException {
    public AssertionFailedException(String message) {
        super(message);
    }

    public AssertionFailedException(String expected, String actual, String message) {
        this((message != null ? "\n- " + message + "\n- " : "") + "Expected [" + expected + "] but was [" + actual + "]");
    }

    public AssertionFailedException(String expected, String actual) {
        this(expected, actual, null);
    }
}
