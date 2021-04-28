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

    public void testAssertArrayEquals00() {
        assertArrayEquals(new Object[0], new Object[1]);
    }

    public void testAssertArrayEquals01() {
        assertArrayEquals("Fail assertArrayEquals with Object", new Object[] { new Object() }, new Object[] { new Object() });
    }

    public void testAssertArrayEquals02() {
        assertArrayEquals(new double[0], new double[1]);
    }

    public void testAssertArrayEquals03() {
        assertArrayEquals("Fail assertArrayEquals with double", new double[] { 1.0 }, new double[] { 2.0 });
    }

    public void testAssertArrayEquals04() {
        assertArrayEquals(new double[0], new double[1], 1.0);
    }

    public void testAssertArrayEquals05() {
        assertArrayEquals("Fail assertArrayEquals with double delta", new double[] { 4.0 }, new double[] { 5.0 }, 0.5);
    }

    public void testAssertArrayEquals06() {
        assertArrayEquals(new float[0], new float[1]);
    }

    public void testAssertArrayEquals07() {
        assertArrayEquals("Fail assertArrayEquals with float", new float[] { 1.0f }, new float[] { 2.0f });
    }

    public void testAssertArrayEquals08() {
        assertArrayEquals(new float[0], new float[1], 1.0f);
    }

    public void testAssertArrayEquals09() {
        assertArrayEquals("Fail assertArrayEquals with float delta", new float[] { 4.0f }, new float[] { 5.0f }, 0.5f);
    }

    public void testAssertArrayEquals10() {
        assertArrayEquals(new long[0], new long[1]);
    }

    public void testAssertArrayEquals11() {
        assertArrayEquals("Fail assertArrayEquals with long", new long[] { 6L }, new long[] { 9L });
    }

    public void testAssertArrayEquals12() {
        assertArrayEquals(new boolean[0], new boolean[1]);
    }

    public void testAssertArrayEquals13() {
        assertArrayEquals("Fail assertArrayEquals with boolean", new boolean[] { false }, new boolean[] { true });
    }

    public void testAssertArrayEquals14() {
        assertArrayEquals(new byte[0], new byte[1]);
    }

    public void testAssertArrayEquals15() {
        assertArrayEquals("Fail assertArrayEquals with byte", new byte[] { 1 }, new byte[] { 2 });
    }

    public void testAssertArrayEquals16() {
        assertArrayEquals(new char[0], new char[1]);
    }

    public void testAssertArrayEquals17() {
        assertArrayEquals("Fail assertArrayEquals with char", new char[] { 'M' }, new char[] { 'U' });
    }

    public void testAssertArrayEquals18() {
        assertArrayEquals(new short[0], new short[1]);
    }

    public void testAssertArrayEquals19() {
        assertArrayEquals("Fail assertArrayEquals with short", new short[] { 5 }, new short[] { 6 });
    }

    public void testAssertArrayEquals20() {
        assertArrayEquals(new int[0], new int[1]);
    }

    public void testAssertArrayEquals21() {
        assertArrayEquals("Fail assertArrayEquals with int", new int[] { 3 }, new int[] { 4 });
    }
}
