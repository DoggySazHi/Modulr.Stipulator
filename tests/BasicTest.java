import static org.junit.jupiter.api.Assertions.*;

public class BasicTest {
    @SuppressWarnings("ObviousNullCheck")
    public static void main(String[] args) {
        assertTrue(true);
        assertFalse(false);
        assertNull(null);
        assertNotNull(new Object());
        assertEquals((byte) 42, (byte) 42);
    }
}
