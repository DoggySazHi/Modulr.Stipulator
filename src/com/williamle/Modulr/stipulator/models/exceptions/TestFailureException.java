package com.williamle.modulr.stipulator.models.exceptions;

public class TestFailureException extends RuntimeException {
    public TestFailureException(String message) {
        super(message != null ? message : "Test was marked as a failure.");
    }

    public TestFailureException() {
        this(null);
    }
}
