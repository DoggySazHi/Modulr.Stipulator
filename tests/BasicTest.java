import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ALL")
public class BasicTest {
    @Test
    public static void staticTestMethod() {
        assertTrue(true);
        assertFalse(false);
        assertNull(null);
        assertNotNull(new Object());
        assertEquals((byte) 42, (byte) 42);
    }

    @Test
    public void nonStaticTestMethod() {
        assertTrue(true);
        assertFalse(false);
        assertNull(null);
        assertNotNull(new Object());
        assertEquals((byte) 42, (byte) 42);
        assertThrows(() -> {
            var maths = 5 / 0;
        }, ArithmeticException.class);
        assertTimeout(Duration.ofSeconds(1), () -> {
            var hello = "world";
        });
    }
}
