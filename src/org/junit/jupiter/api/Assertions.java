package org.junit.jupiter.api;

import com.williamle.Modulr.Stipulator.Models.Exceptions.AssertionFailedException;
import com.williamle.Modulr.Stipulator.Models.Exceptions.TestFailureException;
import com.williamle.Modulr.Stipulator.Models.Runner;
import com.williamle.Modulr.Stipulator.Settings;

import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;

// Only exists for drop-in compatibility with JUnit 5.
public class Assertions {

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
        if (!Objects.equals(expected, actual))
            throw new AssertionFailedException(toStringObj(expected), toStringObj(actual), message);
    }

    public static void assertEquals(Object expected, Object actual) {
        assertEquals(expected, actual, null);
    }

    // assertArrayEquals

    public static void assertArrayEquals(boolean[] expected, boolean[] actual) {
        if (Arrays.equals(expected, actual))
            throw new AssertionFailedException(toStringObj(expected), toStringObj(actual));
    }

    public static void assertArrayEquals(byte[] expected, byte[] actual) {
        if (Arrays.equals(expected, actual))
            throw new AssertionFailedException(toStringObj(expected), toStringObj(actual));
    }

    public static void assertArrayEquals(char[] expected, char[] actual) {
        if (Arrays.equals(expected, actual))
            throw new AssertionFailedException(toStringObj(expected), toStringObj(actual));
    }

    public static void assertArrayEquals(double[] expected, double[] actual) {
        if (Arrays.equals(expected, actual))
            throw new AssertionFailedException(toStringObj(expected), toStringObj(actual));
    }

    public static void assertArrayEquals(float[] expected, float[] actual) {
        if (!Arrays.equals(expected, actual))
            throw new AssertionFailedException(toStringObj(expected), toStringObj(actual));
    }

    public static void assertArrayEquals(short[] expected, short[] actual) {
        if (!Arrays.equals(expected, actual))
            throw new AssertionFailedException(toStringObj(expected), toStringObj(actual));
    }

    public static void assertArrayEquals(int[] expected, int[] actual) {
        if (!Arrays.equals(expected, actual))
            throw new AssertionFailedException(toStringObj(expected), toStringObj(actual));
    }

    public static void assertArrayEquals(long[] expected, long[] actual) {
        if (!Arrays.equals(expected, actual))
            throw new AssertionFailedException(toStringObj(expected), toStringObj(actual));
    }

    public static void assertArrayEquals(Object[] expected, Object[] actual) {
        if (!Arrays.equals(expected, actual))
            throw new AssertionFailedException(toStringObj(expected), toStringObj(actual));
    }

    // assertNotEquals

    // assertThrows

    public static void assertThrows(Runner function) {
        var thrown = false;
        try {
            function.run();
        } catch (Throwable ignore) {
            thrown = true;
        }
        if (!thrown)
            throw new AssertionFailedException("<exception thrown>", "<no exception>");
    }

    // assertNotThrow

    public static void assertDoesNotThrow(Runner function) {
        // I mean, it exists. Whether it's useful (since it'll get caught anyways), I don't know.
        try {
            function.run();
        } catch (Throwable thrown) {
            throw new AssertionFailedException("<no exception>", thrown.toString());
        }
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
                return obj.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(obj)) + " (toString failed)";
            }
        }
        return obj.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(obj));
    }
}
