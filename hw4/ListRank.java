/*
 * 
 * Overall TC: O(logn)
 * 
 */

 public class ListRank extends LLP {
    private int[] parent;
    private int n;

    private int[] result;
    private int root;

    // parent[i] tells us the index of the parent of node i
    // the root r has parent[r] = -1
    public ListRank(int[] parent) {
        super(parent.length);

        this.parent = parent; // use as the next array
        this.n = parent.length;
        this.result = new int[n];
    }

    @Override
    public void initializeState() {
        for (int i = 0; i < n; i++) {
            result[i] = 1;
        }

        for (int i = 0; i < n; i++) {
            if (parent[i] == -1) root = i;
        }
    }

    @Override
    public boolean forbidden(int j) {
        if (j == root) return false;
        if (parent[j] != root) return true;

        return false;
    }

    @Override
    public void advance(int j) {
        result[j] = result[j] + result[parent[j]];
        parent[j] = parent[parent[j]];
    }

    // This method will be called after solve()
    public int[] getSolution() {
        // Return the distance of every node to the root
        result[root] = 0;
        return result;
    }
}
