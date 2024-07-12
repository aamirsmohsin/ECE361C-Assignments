/*
 * 
 * Overall TC: O(n)
 * 
 */

public class JobScheduling extends LLP {
    private int[] result;
    private int[] time;
    private int[][] prerequisites;

    // time[i] is the amount of time job i takes to complete
    // prerequisites[i] is a list of jobs that job i depends on
    public JobScheduling(int[] time, int[][] prerequisites) {
        super(time.length);

        this.result = new int[time.length];
        this.time = time;
        this.prerequisites = prerequisites;
    }

    @Override
    public void initializeState() {
        for (int i = 0; i < time.length; i++) {
            result[i] = 0;
        }
    }

    @Override
    public boolean forbidden(int j) {
        if (result[j] < time[j]) return true;

        for (int i = 0; i < prerequisites[j].length; i++) {
            if (result[j] < time[j] + result[prerequisites[j][i]]) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void advance(int j) {
        int toAdvance = 0;

        for (int i = 0; i < prerequisites[j].length; i++) {
            toAdvance = Math.max(toAdvance, result[prerequisites[j][i]]);
        }

        result[j] = Math.max(result[j], toAdvance + time[j]);
    }

    // This method will be called after solve()
    public int[] getSolution() {
        // Return the completion time for each job
        return result;
    }
}
