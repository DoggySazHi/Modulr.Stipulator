import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BasicTest {
    @Test
    @SuppressWarnings("ObviousNullCheck")
    public static void staticTestMethod() {
        assertTrue(true);
        assertFalse(false);
        assertNull(null);
        assertNotNull(new Object());
        assertEquals((byte) 42, (byte) 42);
    }

    @Test
    @SuppressWarnings("ObviousNullCheck")
    public void nonStaticTestMethod() {
        assertTrue(true);
        assertFalse(false);
        assertNull(null);
        assertNotNull(new Object());
        assertEquals((byte) 42, (byte) 42);
    }
}
