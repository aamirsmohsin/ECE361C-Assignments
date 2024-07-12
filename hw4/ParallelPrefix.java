/*
 * 
 * Overall TC: O(logn)
 * 
 */

public class ParallelPrefix extends LLP {
    private int[] A;
    private int[] S;
    private int[] result;
    private int n;

    // A is an input array that we want to compute the prefix scan for
    // S is the pre-computed summation tree (reduction), computed using LLP-Reduce
    public ParallelPrefix(int[] A, int[] S) {
        super(A.length * 2);

        this.A = A; // length n
        this.S = S; // length n - 1
        this.n = A.length;
        this.result = new int[2 * n]; // length 2n
    }

    @Override
    public void initializeState() {
        for (int i = 0; i < 2 * n; i++) {
            result[i] = Integer.MIN_VALUE;
        }
    }

    @Override
    public boolean forbidden(int j) {
        if (j == 0) {
            return false;
        }

        if (j == 1) {
            if (result[j] < 0) return true;
        } else if (j % 2 == 0) {
            if (result[j] < result[(j / 2)]) return true;
        } else if (j % 2 == 1 && j < n) {
            if (result[j] < S[(j - 1) - 1] + result[(j / 2)]) return true;
        } else if (j % 2 == 1 && j > n) {
            if (result[j] < A[j - n - 1] + result[(j / 2)]) return true;
        }

        return false;
    }

    @Override
    public void advance(int j) {
        if (j == 1) {
            result[j] = 0;
        } else if (j % 2 == 0) {
            result[j] = result[(j / 2)];
        } else if (j % 2 == 1 && j < n) {
            result[j] = S[(j - 1) - 1] + result[(j / 2)];
        } else if (j % 2 == 1 && j > n) {
            result[j] = A[j - n - 1] + result[(j / 2)];
        }
    }

    // This method will be called after solve()
    public int[] getSolution() {
        // Return only the prefix scan part of the state vector
        // i.e. return the last n elements

        int[] finalRes = new int[n];

        for (int i = n; i < 2 * n; i++) {
            finalRes[i - n] = result[i];
        }

        return finalRes;
    }
}
