package question5;

import org.junit.Test;
import static org.junit.Assert.*;

public class SimpleTest {

    @Test
    public void TestTournament() {
        int res = question5.Bakery.PIncrement.parallelIncrement(0, 1);
        assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
    }

    @Test
    public void TestAtomicInteger() {
        int res = question5.AtomicInteger.PIncrement.parallelIncrement(0, 1);
        assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
    }

    @Test
    public void TestSynchronized() {
        int res = question5.Synchronized.PIncrement.parallelIncrement(0, 1);
        assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
    }

    @Test
    public void TestReentrantLock() {
        int res = question5.ReentrantLock.PIncrement.parallelIncrement(0, 1);
        assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
    }
}
