package org.junit.jupiter.api;

import com.williamle.modulr.stipulator.models.LogSeverity;
import com.williamle.modulr.stipulator.models.exceptions.AssertionFailedException;
import com.williamle.modulr.stipulator.models.exceptions.TestFailureException;
import com.williamle.modulr.stipulator.models.exceptions.TimeoutException;
import com.williamle.modulr.stipulator.models.Executable;
import com.williamle.modulr.stipulator.Settings;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;

// Only exists for drop-in compatibility with JUnit 5.

public class Assertions {

    public static void fail() {
        throw new TestFailureException();
    }

    public static void fail(String message) {
        throw new TestFailureException(message);
    }

    // assertTrue

    public static void assertTrue(boolean value, String message) {
        if (!value)
            throw new AssertionFailedException("true", "false", message);
    }

    public static void assertTrue(boolean value) {
        assertTrue(value, null);
    }

    // assertFalse

    public static void assertFalse(boolean value, String message) {
        if (value)
            throw new AssertionFailedException("false", "true", message);
    }

    public static void assertFalse(boolean value) {
        assertFalse(value, null);
    }

    // assertNull

    public static void assertNull(Object value, String message) {
        if (value != null)
            throw new AssertionFailedException("false", "true", message);
    }

    public static void assertNull(Object value) {
        assertNull(value, null);
    }

    // assertNull

    public static void assertNotNull(Object value, String message) {
        if (value == null)
            throw new AssertionFailedException("false", "true", message);
    }

    public static void assertNotNull(Object value) {
        assertNotNull(value, null);
    }

    // assertEquals

    public static void assertEquals(boolean expected, boolean actual, String message) {
        if (expected != actual)
            throw new AssertionFailedException(expected + "", actual + "", message);
    }

    public static void assertEquals(boolean expected, boolean actual) {
        assertEquals(expected, actual, null);
    }

    public static void assertEquals(byte expected, byte actual, String message) {
        if (expected != actual)
            throw new AssertionFailedException(expected + "", actual + "", message);
    }

    public static void assertEquals(byte expected, byte actual) {
        assertEquals(expected, actual, null);
    }

    public static void assertEquals(char expected, char actual, String message) {
        if (expected != actual)
            throw new AssertionFailedException(expected + "", actual + "", message);
    }

    public static void assertEquals(char expected, char actual) {
        assertEquals(expected, actual, null);
    }

    public static void assertEquals(double expected, double actual, double delta, String message) {
        if (Math.abs(expected - actual) > delta)
            throw new AssertionFailedException(expected + "", actual + "", message);
    }

    public static void assertEquals(double expected, double actual, double delta) {
        assertEquals(expected, actual, delta, null);
    }

    public static void assertEquals(double expected, double actual, String message) {
        assertEquals(expected, actual, 0, message);
    }

    public static void assertEquals(double expected, double actual) {
        assertEquals(expected, actual, null);
    }

    public static void assertEquals(float expected, float actual, float delta, String message) {
        if (Math.abs(expected - actual) > delta)
            throw new AssertionFailedException(expected + "", actual + "", message);
    }

    public static void assertEquals(float expected, float actual, String message) {
        assertEquals(expected, actual, 0, message);
    }

    public static void assertEquals(float expected, float actual, float delta) {
        assertEquals(expected, actual, delta, null);
    }

    public static void assertEquals(float expected, float actual) {
        assertEquals(expected, actual, null);
    }

    public static void assertEquals(short expected, short actual, String message) {
        if (expected != actual)
            throw new AssertionFailedException(expected + "", actual + "", message);
    }

    public static void assertEquals(short expected, short actual) {
        assertEquals(expected, actual, null);
    }

    public static void assertEquals(int expected, int actual, String message) {
        if (expected != actual)
            throw new AssertionFailedException(expected + "", actual + "", message);
    }

    public static void assertEquals(int expected, int actual) {
        assertEquals(expected, actual, null);
    }

    public static void assertEquals(long expected, long actual, String message) {
        if (expected != actual)
            throw new AssertionFailedException(expected + "", actual + "", message);
    }

    public static void assertEquals(long expected, long actual) {
        assertEquals(expected, actual, null);
    }

    public static void assertEquals(Object expected, Object actual, String message) {
        var exception = new AssertionFailedException(toStringObj(expected), toStringObj(actual), message);
        if (Objects.equals(expected, actual)) // Handles if they're both null
            return;
        if ((expected == null) ^ (actual == null)) // If only one is null, throw early.
            throw exception;
        // If they're not both the same class or not both arrays, throw.
        if (!expected.getClass().equals(actual.getClass()) || !expected.getClass().isArray())
            throw exception;
        // Pass the rest to the array checker.
        assertArrayEquals((Object[]) expected, (Object[]) actual, message);
    }

    public static void assertEquals(Object expected, Object actual) {
        assertEquals(expected, actual, null);
    }

    // assertArrayEquals

    public static void assertArrayEquals(boolean[] expected, boolean[] actual, String message) {
        if (!Arrays.equals(expected, actual))
            throw new AssertionFailedException(toStringObj(expected), toStringObj(actual), message);
    }

    public static void assertArrayEquals(boolean[] expected, boolean[] actual) {
        assertArrayEquals(expected, actual, null);
    }

    public static void assertArrayEquals(byte[] expected, byte[] actual, String message) {
        if (!Arrays.equals(expected, actual))
            throw new AssertionFailedException(toStringObj(expected), toStringObj(actual), message);
    }

    public static void assertArrayEquals(byte[] expected, byte[] actual) {
        assertArrayEquals(expected, actual, null);
    }

    public static void assertArrayEquals(char[] expected, char[] actual, String message) {
        if (!Arrays.equals(expected, actual))
            throw new AssertionFailedException(toStringObj(expected), toStringObj(actual), message);
    }

    public static void assertArrayEquals(char[] expected, char[] actual) {
        assertArrayEquals(expected, actual, null);
    }

    @SuppressWarnings("DuplicatedCode") // Because I don't know the best way other than actually repeating.
    public static void assertArrayEquals(double[] expected, double[] actual, String message, double delta) {
        var exception = new AssertionFailedException(toStringObj(expected), toStringObj(actual), message);
        if (expected.length != actual.length)
            throw exception;
        for (var i = 0; i < expected.length; i++)
            if (Math.abs(expected[i] - actual[i]) > delta)
                throw exception;
    }

    public static void assertArrayEquals(double[] expected, double[] actual, String message) {
        assertArrayEquals(expected, actual, message, 0);
    }

    public static void assertArrayEquals(double[] expected, double[] actual, double delta) {
        assertArrayEquals(expected, actual, null, delta);
    }

    public static void assertArrayEquals(double[] expected, double[] actual) {
        assertArrayEquals(expected, actual, null, 0);
    }

    @SuppressWarnings("DuplicatedCode")
    public static void assertArrayEquals(float[] expected, float[] actual, String message, float delta) {
        var exception = new AssertionFailedException(toStringObj(expected), toStringObj(actual), message);
        if (expected.length != actual.length)
            throw exception;
        for (var i = 0; i < expected.length; i++)
            if (Math.abs(expected[i] - actual[i]) > delta)
                throw exception;
    }

    public static void assertArrayEquals(float[] expected, float[] actual, String message) {
        assertArrayEquals(expected, actual, message, 0);
    }

    public static void assertArrayEquals(float[] expected, float[] actual, float delta) {
        assertArrayEquals(expected, actual, null, delta);
    }

    public static void assertArrayEquals(float[] expected, float[] actual) {
        assertArrayEquals(expected, actual, null, 0);
    }

    public static void assertArrayEquals(short[] expected, short[] actual, String message) {
        if (!Arrays.equals(expected, actual))
            throw new AssertionFailedException(toStringObj(expected), toStringObj(actual), message);
    }

    public static void assertArrayEquals(short[] expected, short[] actual) {
        assertArrayEquals(expected, actual, null);
    }

    public static void assertArrayEquals(int[] expected, int[] actual, String message) {
        if (!Arrays.equals(expected, actual))
            throw new AssertionFailedException(toStringObj(expected), toStringObj(actual), message);
    }

    public static void assertArrayEquals(int[] expected, int[] actual) {
        assertArrayEquals(expected, actual, null);
    }

    public static void assertArrayEquals(long[] expected, long[] actual, String message) {
        if (!Arrays.equals(expected, actual))
            throw new AssertionFailedException(toStringObj(expected), toStringObj(actual), message);
    }

    public static void assertArrayEquals(long[] expected, long[] actual) {
        assertArrayEquals(expected, actual, null);
    }

    public static void assertArrayEquals(Object[] expected, Object[] actual, String message) {
        if (!Arrays.deepEquals(expected, actual))
            throw new AssertionFailedException(toStringObj(expected), toStringObj(actual), message);
    }

    public static void assertArrayEquals(Object[] expected, Object[] actual) {
        assertArrayEquals(expected, actual, null);
    }

    // assertSame

    public static void assertSame(Object expected, Object actual, String message) {
        if (expected != actual)
            throw new AssertionFailedException(expected + "", actual + "", message);
    }

    public static void assertSame(Object expected, Object actual) {
        assertSame(expected, actual, null);
    }

    // assertNotEquals

    private static AssertionFailedException failNotEquals(String unexpected, String actual, String message) {
        return new AssertionFailedException((message != null ? "\n- " + message + "\n- " : "") + "Didn't expect equality between [" + unexpected + "] but was! [" + actual + "]");
    }

    private static AssertionFailedException failNotEquals(String unexpected, String actual) { return failNotEquals(unexpected, actual, null); }

    public static void assertNotEquals(boolean unexpected, boolean actual, String message) {
        if (unexpected == actual)
            throw failNotEquals(unexpected + "", actual + "", message);
    }

    public static void assertNotEquals(boolean unexpected, boolean actual) {
        assertNotEquals(unexpected, actual, null);
    }

    public static void assertNotEquals(byte unexpected, byte actual, String message) {
        if (unexpected == actual)
            throw failNotEquals(unexpected + "", actual + "", message);
    }

    public static void assertNotEquals(byte unexpected, byte actual) {
        assertNotEquals(unexpected, actual, null);
    }

    public static void assertNotEquals(char unexpected, char actual, String message) {
        if (unexpected == actual)
            throw failNotEquals(unexpected + "", actual + "", message);
    }

    public static void assertNotEquals(char unexpected, char actual) {
        assertNotEquals(unexpected, actual, null);
    }

    public static void assertNotEquals(double unexpected, double actual, double delta, String message) {
        if (Math.abs(unexpected - actual) <= delta)
            throw failNotEquals(unexpected + "", actual + "", message);
    }

    public static void assertNotEquals(double unexpected, double actual, double delta) {
        assertNotEquals(unexpected, actual, delta, null);
    }

    public static void assertNotEquals(double unexpected, double actual, String message) {
        assertNotEquals(unexpected, actual, 0, message);
    }

    public static void assertNotEquals(double unexpected, double actual) {
        assertNotEquals(unexpected, actual, null);
    }

    public static void assertNotEquals(float unexpected, float actual, float delta, String message) {
        if (Math.abs(unexpected - actual) <= delta)
            throw failNotEquals(unexpected + "", actual + "", message);
    }

    public static void assertNotEquals(float unexpected, float actual, String message) {
        assertNotEquals(unexpected, actual, 0, message);
    }

    public static void assertNotEquals(float unexpected, float actual, float delta) {
        assertNotEquals(unexpected, actual, delta, null);
    }

    public static void assertNotEquals(float unexpected, float actual) {
        assertNotEquals(unexpected, actual, null);
    }

    public static void assertNotEquals(short unexpected, short actual, String message) {
        if (unexpected == actual)
            throw failNotEquals(unexpected + "", actual + "", message);
    }

    public static void assertNotEquals(short unexpected, short actual) {
        assertNotEquals(unexpected, actual, null);
    }

    public static void assertNotEquals(int unexpected, int actual, String message) {
        if (unexpected == actual)
            throw failNotEquals(unexpected + "", actual + "", message);
    }

    public static void assertNotEquals(int unexpected, int actual) {
        assertNotEquals(unexpected, actual, null);
    }

    public static void assertNotEquals(long unexpected, long actual, String message) {
        if (unexpected == actual)
            throw failNotEquals(unexpected + "", actual + "", message);
    }

    public static void assertNotEquals(long unexpected, long actual) {
        assertNotEquals(unexpected, actual, null);
    }

    public static void assertNotEquals(Object unexpected, Object actual, String message) {
        var exception = failNotEquals(toStringObj(unexpected), toStringObj(actual), message);
        if ((unexpected == null) ^ (actual == null)) // If only one is null, return early.
            return;
        if (unexpected == null) // If both are null, raise the exception.
            throw exception;
        if (unexpected.getClass().isArray() && actual.getClass().isArray()) {
            assertArrayEquals((Object[]) unexpected, (Object[]) actual, message);
            return; // So we don't fall into the next part; an exception would be thrown by assertArrayEquals
        }
        if (Objects.equals(unexpected, actual))
            throw exception;
    }

    public static void assertNotEquals(Object unexpected, Object actual) {
        assertNotEquals(unexpected, actual, null);
    }

    // assertArrayNotEquals

    public static void assertArrayNotEquals(boolean[] unexpected, boolean[] actual, String message) {
        if (Arrays.equals(unexpected, actual))
            throw failNotEquals(toStringObj(unexpected), toStringObj(actual), message);
    }

    public static void assertArrayNotEquals(boolean[] unexpected, boolean[] actual) {
        assertArrayNotEquals(unexpected, actual, null);
    }

    public static void assertArrayNotEquals(byte[] unexpected, byte[] actual, String message) {
        if (Arrays.equals(unexpected, actual))
            throw failNotEquals(toStringObj(unexpected), toStringObj(actual), message);
    }

    public static void assertArrayNotEquals(byte[] unexpected, byte[] actual) {
        assertArrayNotEquals(unexpected, actual, null);
    }

    public static void assertArrayNotEquals(char[] unexpected, char[] actual, String message) {
        if (Arrays.equals(unexpected, actual))
            throw failNotEquals(toStringObj(unexpected), toStringObj(actual), message);
    }

    public static void assertArrayNotEquals(char[] unexpected, char[] actual) {
        assertArrayNotEquals(unexpected, actual, null);
    }

    @SuppressWarnings("DuplicatedCode") // Because I don't know the best way other than actually repeating.
    public static void assertArrayNotEquals(double[] unexpected, double[] actual, String message, double delta) {
        var exception = failNotEquals(toStringObj(unexpected), toStringObj(actual), message);
        if (unexpected.length != actual.length)
            throw exception;
        for (var i = 0; i < unexpected.length; i++)
            if (Math.abs(unexpected[i] - actual[i]) > delta)
                throw exception;
    }

    public static void assertArrayNotEquals(double[] unexpected, double[] actual, String message) {
        assertArrayNotEquals(unexpected, actual, message, 0);
    }

    public static void assertArrayNotEquals(double[] unexpected, double[] actual, double delta) {
        assertArrayNotEquals(unexpected, actual, null, delta);
    }

    @SuppressWarnings("DuplicatedCode")
    public static void assertArrayNotEquals(float[] unexpected, float[] actual, String message, float delta) {
        var exception = failNotEquals(toStringObj(unexpected), toStringObj(actual), message);
        if (unexpected.length != actual.length)
            throw exception;
        for (var i = 0; i < unexpected.length; i++)
            if (Math.abs(unexpected[i] - actual[i]) > delta)
                throw exception;
    }

    public static void assertArrayNotEquals(float[] unexpected, float[] actual, String message) {
        assertArrayNotEquals(unexpected, actual, message, 0);
    }

    public static void assertArrayNotEquals(float[] unexpected, float[] actual, float delta) {
        assertArrayNotEquals(unexpected, actual, null, delta);
    }

    public static void assertArrayNotEquals(short[] unexpected, short[] actual, String message) {
        if (Arrays.equals(unexpected, actual))
            throw failNotEquals(toStringObj(unexpected), toStringObj(actual), message);
    }

    public static void assertArrayNotEquals(short[] unexpected, short[] actual) {
        assertArrayNotEquals(unexpected, actual, null);
    }

    public static void assertArrayNotEquals(int[] unexpected, int[] actual, String message) {
        if (Arrays.equals(unexpected, actual))
            throw failNotEquals(toStringObj(unexpected), toStringObj(actual), message);
    }

    public static void assertArrayNotEquals(int[] unexpected, int[] actual) {
        assertArrayNotEquals(unexpected, actual, null);
    }

    public static void assertArrayNotEquals(long[] unexpected, long[] actual, String message) {
        if (Arrays.equals(unexpected, actual))
            throw failNotEquals(toStringObj(unexpected), toStringObj(actual), message);
    }

    public static void assertArrayNotEquals(long[] unexpected, long[] actual) {
        assertArrayNotEquals(unexpected, actual, null);
    }

    public static void assertArrayNotEquals(Object[] unexpected, Object[] actual, String message) {
        if (Arrays.equals(unexpected, actual))
            throw failNotEquals(toStringObj(unexpected), toStringObj(actual), message);
    }

    public static void assertArrayNotEquals(Object[] unexpected, Object[] actual) {
        assertArrayNotEquals(unexpected, actual, null);
    }

    // assertNotSame

    public static void assertNotSame(Object expected, Object actual, String message) {
        if (expected == actual)
            throw new AssertionFailedException(expected + "", actual + "", message);
    }

    public static void assertNotSame(Object expected, Object actual) {
        assertNotSame(expected, actual, null);
    }

    // assertThrows

    public static Throwable assertThrows(Executable function, Class<? extends Throwable> exception, String message) {
        Throwable caught = null;
        var expectedMessage = exception != null ? exception.getName() : "<exception thrown>";
        try {
            function.run();
        } catch (Throwable ex) {
            caught = ex;
        }
        if (caught == null)
            throw new AssertionFailedException(expectedMessage, "<no exception>", message);
        if (exception != null && !caught.getClass().equals(exception))
            throw new AssertionFailedException(expectedMessage, caught.getClass().getName(), message);
        return caught;
    }

    public static Throwable assertThrows(Executable function, Class<? extends Throwable> exception) {
        return assertThrows(function, exception, null);
    }

    public static Throwable assertThrows(Executable function, String message) {
        return assertThrows(function, null, message);
    }

    public static Throwable assertThrows(Executable function) {
        return assertThrows(function, null, null);
    }

    // assertNotThrow

    public static void assertDoesNotThrow(Executable function, String message) {
        // I mean, it exists. Whether it's useful (since it'll get caught anyways), I don't know.
        try {
            function.run();
        } catch (Throwable thrown) {
            throw new AssertionFailedException("<no exception>", thrown.toString(), message);
        }
    }

    public static void assertDoesNotThrow(Executable function) {
        assertDoesNotThrow(function, null);
    }

    // assertTimeout

    public static void assertTimeout(Duration timeout, Executable executable, String message) {
        var start = Instant.now();
        try {
            executable.run();
        } catch (Throwable ex) {
            if (ex instanceof AssertionFailedException)
                throw (AssertionFailedException) ex;
            throw new RuntimeException(ex); // Hope the TestManager catches this one!
        }
        var time = Duration.between(start, Instant.now());
        var duration = time.compareTo(timeout);
        if (duration > 0)
            throw new TimeoutException(timeout.toMillis(), time.toMillis(), message);
    }

    public static void assertTimeout(Duration timeout, Executable executable) {
        assertTimeout(timeout, executable, null);
    }

    // assertTimeoutPreemptively

    public static void assertTimeoutPreemptively(Duration timeout, Executable executable, String message) {
        var start = Instant.now();
        try {
            CompletableFuture.runAsync(() -> {
                try {
                    executable.run();
                } catch (Throwable ex) {
                    if (ex instanceof AssertionFailedException) // By us!
                        throw (AssertionFailedException) ex;
                    throw new RuntimeException(ex);
                }
            }).orTimeout(timeout.toMillis(), TimeUnit.MILLISECONDS).join();
        } catch (Throwable ex) {
            if (ex instanceof CompletionException)
                ex = ex.getCause();
            if (ex instanceof java.util.concurrent.TimeoutException) {
                var time = Duration.between(start, Instant.now());
                throw new TimeoutException(timeout.toMillis(), time.toMillis(), message);
            }
            else if (ex instanceof AssertionFailedException) // By us!
                throw (AssertionFailedException) ex;
            throw new RuntimeException(ex); // By them!
        }
    }

    public static void assertTimeoutPreemptively(Duration timeout, Executable executable) {
        assertTimeoutPreemptively(timeout, executable, null);
    }

    private static String toStringObj(Object obj) {
        if (obj == null)
            return "null";
        else if (Settings.UseToString) {
            try {
                if (obj.getClass().isArray())
                    return Arrays.deepToString((Object[]) obj);
                return obj.toString();
            }
            catch (Exception ignore) {
                if (Settings.LogLevel == LogSeverity.VERBOSE)
                return obj.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(obj)) + " (toString failed)";
            }
        }
        return obj.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(obj));
    }
}
