package question4;

import org.junit.Test;
import static org.junit.Assert.*;

public class SimpleTest {

    @Test
    public void TestBasic() {
        int[] A = { 1 };
        int frequency = Frequency.parallelFreq(1, A, 8);
        assertTrue("Result is " + frequency + ", expected frequency of 1 is 1.", frequency == 1);
    }

    @Test
    public void TestArray() {
        int[] A = { 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 33, 3, 3, 3, 3, 3, 3, 4, 5, 423, 2342, 423, 4, 23, 23, 423,
                7, 23, 3, 23, 2, 5, 11, 232, 32, 32, 32, 65 };
        int frequency = Frequency.parallelFreq(3, A, 7);
        assertTrue("Result is " + frequency + ", expected frequency of 3 is 19.", frequency == 19);
        frequency = Frequency.parallelFreq(23, A, 7);
        assertTrue("Result is " + frequency + ", expected frequency of 23 is 4.", frequency == 4);
        frequency = Frequency.parallelFreq(32, A, 7);
        assertTrue("Result is " + frequency + ", expected frequency of 32 is 3.", frequency == 3);
    }

    @Test
    public void TestNoElements() {
        int[] A = { };
        int frequency = Frequency.parallelFreq(1, A, 8);
        assertTrue("Result is " + frequency + ", expected frequency of 1 is 1.", frequency == -1);
    }

    @Test
    public void TestNoThreads() {
        int[] A = { };
        int frequency = Frequency.parallelFreq(1, A, 0);
        assertTrue("Result is " + frequency + ", expected frequency of 1 is 1.", frequency == -1);
    }

    @Test
    public void Test100Elements() {
        int[] A = { 43, 17, 8, 3, 62, 15, 29, 14, 97, 11, 55, 73, 5, 71, 60, 48, 66, 78, 88, 39,
            82, 36, 80, 91, 50, 28, 9, 25, 17, 23, 41, 98, 95, 70, 27, 20, 33, 54, 35, 10,
            12, 77, 1, 19, 74, 89, 26, 2, 76, 68, 31, 65, 85, 49, 79, 99, 32, 66, 46, 63,
            57, 56, 7, 23, 58, 18, 64, 0, 83, 30, 85, 22, 44, 93, 90, 37, 40, 69, 24, 75,
            98, 92, 29, 42, 53, 52, 9, 4, 53, 88, 74, 38, 59, 16, 22, 66, 48, 7, 13, 11,
            51, 42, 53, 81, 71, 68, 68, 48, 50, 91, 70, 65, 13, 89, 23, 67, 83, 72, 55 };

        int frequency = Frequency.parallelFreq(43, A, 7);
        assertTrue("Result is " + frequency + ", expected frequency of 1 is 1.", frequency == 1);
        frequency = Frequency.parallelFreq(17, A, 7);
        assertTrue("Result is " + frequency + ", expected frequency of 1 is 1.", frequency == 2);
        frequency = Frequency.parallelFreq(66, A, 3);
        assertTrue("Result is " + frequency + ", expected frequency of 1 is 1.", frequency == 3);
        frequency = Frequency.parallelFreq(83, A, 100);
        assertTrue("Result is " + frequency + ", expected frequency of 1 is 1.", frequency == 2);
        frequency = Frequency.parallelFreq(83, A, 1000);
        assertTrue("Result is " + frequency + ", expected frequency of 1 is 1.", frequency == 2);
    }
}
