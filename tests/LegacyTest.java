import org.junit.Test;

// Combines JUnit 3 and 4 techniques into one class.
public class LegacyTest extends junit.framework.TestCase {
    private boolean setUp;

    @Override
    public void setUp() {
        setUp = true;
    }

    public void testSetUp() {
        assertTrue(setUp);
    }

    public void testSimilarities() {
        var temp = new Object();
        assertSame(temp, temp);
        assertNotSame(temp, new Object());
    }

    public void notATestMethod() {
        fail("Executed non-test method!");
    }

    @Test
    public void namedNotATestButIsOne() {
        assertTrue(true);
    }
}
