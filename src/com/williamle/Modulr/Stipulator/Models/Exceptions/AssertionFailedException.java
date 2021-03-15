package com.williamle.Modulr.Stipulator.Models.Exceptions;

public class AssertionFailedException extends TestFailureException {
    public AssertionFailedException(String expected, String actual, String message) {
        super((message != null ? message + "\n- " : "") + "Expected [" + expected + "] but was [" + actual + "]");
    }

    public AssertionFailedException(String expected, String actual) {
        this(expected, actual, null);
    }
}
