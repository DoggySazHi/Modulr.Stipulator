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
        assertFalse(true);
    }

    public void testIsFalse1() {
        assertFalse("Failed assertFalse", true);
    }

    public void testFail0() {
        fail();
    }

    public void testFail1() {
        fail("Generic fail message");
    }

    public void assertArrayEquals00() {
        assertArrayEquals(new Object[0], new Object[0]);
    }

    public void assertArrayEquals01() {
        assertArrayEquals("Fail assertArrayEquals with Object", new Object[] { new Object() }, new Object[] { new Object() });
    }

    public void assertArrayEquals02() {
        assertArrayEquals(new double[0], new double[0]);
    }

    public void assertArrayEquals03() {
        assertArrayEquals("Fail assertArrayEquals with double", new double[] { 1.0 }, new double[] { 2.0 });
    }

    public void assertArrayEquals04() {
        assertArrayEquals(new double[0], new double[0], 1.0);
    }

    public void assertArrayEquals05() {
        assertArrayEquals("Fail assertArrayEquals with double delta", new double[] { 4.0 }, new double[] { 5.0 }, 0.5);
    }

    public void assertArrayEquals06() {
        assertArrayEquals(new float[0], new float[0]);
    }

    public void assertArrayEquals07() {
        assertArrayEquals("Fail assertArrayEquals with float", new float[] { 1.0f }, new float[] { 2.0f });
    }

    public void assertArrayEquals08() {
        assertArrayEquals(new float[0], new float[0], 1.0f);
    }

    public void assertArrayEquals09() {
        assertArrayEquals("Fail assertArrayEquals with float delta", new float[] { 4.0f }, new float[] { 5.0f }, 0.5f);
    }
}
