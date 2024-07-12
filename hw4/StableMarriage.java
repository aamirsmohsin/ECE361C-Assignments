/*
 * 
 * Overall TC: O(n^2)
 * 
 */

 public class StableMarriage extends LLP {
    private int[][] mprefs; // man -> [pref for women]
    private int[][] wprefs;

    private int[][] inverseM; // man -> [rank of women]
    private int[][] inverseW;

    private int n;
    private int[] result;

    // mprefs[i][k] is man i's kth choice
    // wprefs[i][k] is woman i's kth choice
    public StableMarriage(int[][] mprefs, int[][] wprefs) {
        super(mprefs.length);

        this.mprefs = mprefs;
        this.wprefs = wprefs;

        this.n = mprefs.length;

        this.result = new int[n];

        this.inverseM = new int[n][n];
        this.inverseW = new int[n][n];
    }

    @Override
    public void initializeState() {
        for (int i = 0; i < n; i++) {
            result[i] = 0;
        }

        // Create Inverse Preference Lists

        // [1, 2, 0]
        // [2, 0, 1]

        for (int i = 0; i < n; i++) { // Man
            for (int j = 0; j < n; j++) {
                int woman = mprefs[i][j];
                inverseM[i][woman] = j;
            }
        }

        for (int i = 0; i < n; i++) { // Woman
            for (int j = 0; j < n; j++) {
                int man = wprefs[i][j];
                inverseW[i][man] = j;
            }
        }
    }

    @Override
    public boolean forbidden(int j) {
        int z = mprefs[j][result[j]]; // The woman Z that man J is currently matched with

        // Does there exist a man who has previously proposed to Z that Z prefers more than J?
        for (int i = 0; i < n; i++) {
            for (int k = 0; k <= result[i]; k++) {
                boolean condition1 = z == mprefs[i][k];
                boolean condition2 = inverseW[z][i] < inverseW[z][j];

                if (condition1 && condition2) return true;
            }
        }

        return false;
    }

    @Override
    public void advance(int j) {
        result[j] += 1;
    }

    // This method will be called after solve()
    public int[] getSolution() {
        // Convert Indices to Women
        int[] finalRes = new int[n];

        for (int i = 0; i < n; i++) {
            finalRes[i] = mprefs[i][result[i]];
        }

        return finalRes;
    }
}
