package com.williamle.Modulr.Stipulator.Models.Exceptions;

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
