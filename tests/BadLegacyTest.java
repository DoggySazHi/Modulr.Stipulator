@SuppressWarnings("ALL") // Yeah, it *should* be bad.
public class BadLegacyTest extends junit.framework.TestCase {
    @Override
    public void setUp() {
        throw new RuntimeException("Random exception");
    }

    @Override
    public void tearDown() {
        var output = 1 / 0;
    }

    public void testIsTrue0() {
        assertTrue(false);
    }

    public void testIsTrue1() {
        assertTrue("Failed assertTrue", false);
    }

    public void testIsFalse0() {
        assertTrue(true);
    }

    public void testIsFalse1() {
        assertTrue("Failed assertFalse", true);
    }

    public void testFail0() {
        fail();
    }

    public void testFail1() {
        fail("Generic fail message");
    }
}
