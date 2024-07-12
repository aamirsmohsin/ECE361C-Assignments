/*
 * 
 * Overall TC: O(logn)
 * 
 */

public class ParallelReduce extends LLP {
    private int[] result;
    private int[] A;
    private int n;

    // A is an input array that we want to compute the reduction for
    public ParallelReduce(int[] A) {
        super(A.length - 1);

        this.result = new int[A.length - 1];
        this.A = A;
        this.n = A.length;
    }

    @Override
    public void initializeState() {
        for (int i = 0; i < result.length; i++) {
            result[i] = Integer.MIN_VALUE;
        }
    }

    @Override
    public boolean forbidden(int j) {
        j++;

        if (j >= 1 && j < n / 2) {
            if (result[j - 1] < result[(2 * j) - 1] + result[(2 * j + 1) - 1]) return true;
        } else {
            if (result[j - 1] < A[(2 * j - n + 1) - 1] + A[(2 * j - n + 2) - 1]) return true;
        }

        return false;
    }

    @Override
    public void advance(int j) {
        j++;

        if (j >= 1 && j < n / 2) {
            result[j - 1] = result[(2 * j) - 1] + result[(2 * j + 1) - 1];
        } else {
            result[j - 1] = A[(2 * j - n + 1) - 1] + A[(2 * j - n + 2) - 1];
        }

        return;
    }

    // This method will be called after solve()
    public int[] getSolution() {
        // Trim the state vector to only the reduce elements
        // Your result should have n-1 elements
        return result;
    }
}
