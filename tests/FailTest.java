import org.junit.jupiter.api.Test;

import java.time.Duration;

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
    public void failAssertThrows0() {
        assertThrows(() -> {
            var neko = 3;
            var miko = 4;
            var reimu = neko + miko;
            var aiShiteru = neko / miko;
        });
    }

    @Test
    public void failAssertThrows1() {
        assertThrows(() -> {
            var goodMath = 5 / 0;
        }, UnsupportedOperationException.class);
    }

    @Test
    public void failAssertNotThrow() {
        assertThrows(() -> {
            var neko = 3;
            var miko = 4;
            var reimu = neko + miko;
            var aiShiteru = neko / miko;
        });
    }

    @Test
    public void failAssertTimeout() {
        assertTimeout(Duration.ofSeconds(1), () -> {
            var now = System.currentTimeMillis();
            while (System.currentTimeMillis() - now < 2000) {}
        });
    }

    @Test
    public void failAssertTimeoutPreemptively() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            var now = System.currentTimeMillis();
            while (System.currentTimeMillis() - now < 2000) {}
        });
    }
}
