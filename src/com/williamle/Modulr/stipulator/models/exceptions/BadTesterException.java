package com.williamle.modulr.stipulator.models.exceptions;

public class BadTesterException extends TestFailureException {
    public BadTesterException (Class<?> tester, String message) {
        super("Tester class \"" + tester.getName() + "\" is invalid!" +
                (message != null ? "\n- " + message : ""));
    }

    public BadTesterException (Class<?> tester) {
        this(tester, null);
    }

    public BadTesterException (String message) {
        super(message);
    }
}
