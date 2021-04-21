package org.junit;

import org.junit.jupiter.api.Assertions;

public class Assert {
    public static void assertTrue(String message, boolean condition) {
        Assertions.assertTrue(condition, message);
    }

    public static void assertTrue(boolean condition) {
        Assertions.assertTrue(condition);
    }

    public static void assertFalse(String message, boolean condition) {
        Assertions.assertFalse(condition, message);
    }

    public static void assertFalse(boolean condition) {
        Assertions.assertFalse(condition);
    }

    public static void fail(String message) {
        Assertions.fail(message);
    }

    public static void fail() {
        Assertions.fail();
    }

    public static void assertEquals(String message, Object expected, Object actual) {
        Assertions.assertEquals(expected, actual, message);
    }

    public static void assertEquals(Object expected, Object actual) {
        Assertions.assertEquals(expected, actual);
    }

    public static void assertEquals(String message, String expected, String actual) {
        Assertions.assertEquals(expected, actual, message);
    }

    public static void assertEquals(String expected, String actual) {
        Assertions.assertEquals(expected, actual);
    }

    public static void assertEquals(String message, double expected, double actual, double delta) {
        Assertions.assertEquals(expected, actual, delta, message);
    }

    public static void assertEquals(double expected, double actual, double delta) {
        Assertions.assertEquals(expected, actual, delta);
    }

    public static void assertEquals(String message, float expected, float actual, float delta) {
        Assertions.assertEquals(expected, actual, delta, message);
    }

    public static void assertEquals(float expected, float actual, float delta) {
        Assertions.assertEquals(expected, actual, delta);
    }

    public static void assertEquals(String message, long expected, long actual) {
        Assertions.assertEquals(expected, actual, message);
    }

    public static void assertEquals(long expected, long actual) {
        Assertions.assertEquals(expected, actual);
    }

    public static void assertEquals(String message, boolean expected, boolean actual) {
        Assertions.assertEquals(expected, actual, message);
    }

    public static void assertEquals(boolean expected, boolean actual) {
        Assertions.assertEquals(expected, actual);
    }

    public static void assertEquals(String message, byte expected, byte actual) {
        Assertions.assertEquals(expected, actual, message);
    }

    public static void assertEquals(byte expected, byte actual) {
        Assertions.assertEquals(expected, actual);
    }

    public static void assertEquals(String message, char expected, char actual) {
        Assertions.assertEquals(expected, actual, message);
    }

    public static void assertEquals(char expected, char actual) {
        Assertions.assertEquals(expected, actual);
    }

    public static void assertEquals(String message, short expected, short actual) {
        Assertions.assertEquals(expected, actual, message);
    }

    public static void assertEquals(short expected, short actual) {
        Assertions.assertEquals(expected, actual);
    }

    public static void assertEquals(String message, int expected, int actual) {
        Assertions.assertEquals(expected, actual, message);
    }

    public static void assertEquals(int expected, int actual) {
        Assertions.assertEquals(expected, actual);
    }

    public static void assertNotNull(Object object) {
        Assertions.assertNotNull(object);
    }

    public static void assertNotNull(String message, Object object) {
        Assertions.assertNotNull(object, message);
    }

    public static void assertNull(Object object) {
        Assertions.assertNull(object);
    }

    public static void assertNull(String message, Object object) {
        Assertions.assertNull(object, message);
    }

    public static void assertSame(String message, Object expected, Object actual) {
        Assertions.assertSame(expected, actual, message);
    }

    public static void assertSame(Object expected, Object actual) {
        Assertions.assertSame(expected, actual);
    }

    public static void assertNotSame(String message, Object expected, Object actual) {
        Assertions.assertNotSame(expected, actual, message);
    }

    public static void assertNotSame(Object expected, Object actual) {
        Assertions.assertNotSame(expected, actual);
    }
}
