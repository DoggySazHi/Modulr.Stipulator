import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ALL") // Yeah, it *should* be bad.
public class FailTest {
    @Test
    public void failAssertTrue() {
        assertTrue(false);
    }

    @Test
    public void failAssertFalse() {
        assertFalse(true);
    }

    @Test
    public void failAssertEquals0() {
        assertEquals("A", "B");
    }

    @Test
    public void failAssertEquals1() {
        assertEquals(null, "B");
    }

    @Test
    public void failAssertEquals2() {
        assertEquals(new Object(), null);
    }

    @Test
    public void failAssertEquals3() {
        assertEquals(6, new double[0]);
    }

    @Test
    public void failAssertEquals4() {
        assertEquals(9L, (short) 420);
    }

    @Test
    public void failAssertNull() {
        assertNull(new Object());
    }

    @Test
    public void failAssertThrows() {
        assertThrows(() -> {
            var neko = 3;
            var miko = 4;
            var reimu = neko + miko;
            var aiShiteru = neko / miko;
        });
    }
}
