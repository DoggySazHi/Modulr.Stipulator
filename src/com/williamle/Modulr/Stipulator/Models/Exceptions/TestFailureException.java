package com.williamle.Modulr.Stipulator.Models.Exceptions;

public class TestFailureException extends RuntimeException {
    public TestFailureException(String message) {
        super(message != null ? message : "Test was marked as a failure.");
    }

    public TestFailureException() {
        this(null);
    }
}
