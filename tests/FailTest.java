import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.time.Duration;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ALL") // Yeah, it *should* be bad.
public class FailTest {
    @Test
    public void failAssertTrue() {
        assertTrue(false, "Failed assertTrue");
    }

    @Test
    public void failAssertFalse() {
        assertFalse(true, "Failed assertFalse");
    }

    @Test
    public void failAssertEquals0() {
        assertEquals("A", "B", "Failed assertEquals");
    }

    @Test
    public void failAssertEquals1() {
        assertEquals(null, "B", "Failed assertEquals");
    }

    @Test
    public void failAssertEquals2() {
        assertEquals(new Object(), null, "Failed assertEquals");
    }

    @Test
    public void failAssertEquals3() {
        assertEquals(6, new double[0], "Failed assertEquals");
    }

    @Test
    public void failAssertEquals4() {
        assertEquals(9L, (short) 420, "Failed assertEquals");
    }

    @Test
    public void failAssertNotEquals0() {
        assertNotEquals("A", "A", "Failed assertNotEquals");
    }

    @Test
    public void failAssertNotEquals1() {
        assertNotEquals(null, null, "Failed assertNotEquals");
    }

    @Test
    public void failAssertNotEquals2() {
        var obj = new Object();
        assertNotEquals(obj, obj, "Failed assertNotEquals");
    }

    @Test
    public void failAssertNotEquals3() {
        assertNotEquals(Arrays.asList("Mukyu", "Patchouli"), Arrays.asList("Mukyu", "Patchouli"), "Failed assertNotEquals");
    }

    @Test
    public void failAssertNotEquals4() {
        assertNotEquals(9L, 9L, "Failed assertNotEquals");
    }

    @Test
    public void failAssertNull() {
        assertNull(new Object(), "Failed assertNull");
    }

    @Test
    public void failAssertThrows0() {
        assertThrows(() -> {
            var neko = 3;
            var miko = 4;
            var reimu = neko + miko;
            var aiShiteru = neko / miko;
        }, "Failed assertThrows");
    }

    @Test
    public void failAssertThrows1() {
        assertThrows(() -> {
            var goodMath = 5 / 0;
        }, UnsupportedOperationException.class, "Failed assertThrows");
    }

    @Test
    public void failAssertNotThrow() {
        assertDoesNotThrow(() -> {
            throw new RuntimeException("Mukyu!");
        }, "Failed assertNotThrow");
    }

    @Test
    public void failAssertTimeout() {
        assertTimeout(Duration.ofSeconds(1), () -> {
            var now = System.currentTimeMillis();
            while (System.currentTimeMillis() - now < 2000) {}
        }, "Failed assertTimeout");
    }

    @Test
    public void failAssertTimeoutPreemptively() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            var now = System.currentTimeMillis();
            while (System.currentTimeMillis() - now < 2000) {}
        }, "Failed assertTimeoutPreemptively");
    }

    @Test
    public void failWithMessage() {
        fail("Generic message.");
    }

    @Test
    public void failAssertionWithMessage() {
        assertEquals(27285, "2HU", "Generic message.");
    }

    @Test
    public void failByException0() {
        Integer.parseInt("lol");
    }

    @Test
    public void failByException1() {
        Integer asdf = null;
        asdf += 1;
    }

    @Test
    public void failByException2() {
        assertTimeout(Duration.ofSeconds(1), () -> {
            Integer asdf = null;
            asdf += 1;
        });
    }

    @Test
    public void failByException3() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            assertTrue(false);
        });
    }

    @Test
    public void failByException4() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            var website = new URL("https://williamle.com");
            var stream = Channels.newChannel(website.openStream());
            var output = new FileOutputStream("index.html");
            output.getChannel().transferFrom(stream, 0, Long.MAX_VALUE);
        }, "Failed assertTimeoutPreemptively with exception");
    }
}
