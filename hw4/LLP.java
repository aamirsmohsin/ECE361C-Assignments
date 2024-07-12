import java.util.ArrayList;

public abstract class LLP {
    // Feel free to add any methods here. Common parameters (e.g. number of processes)
    // can be passed up through a super constructor. Your code will be tested by creating
    // an instance of a sub-class, calling the solve() method below, and then calling the
    // sub-class's getSolution() method. You are free to modify anything else as long as
    // you follow this API (see SimpleTest.java)

    private ArrayList<Integer> forbiddenStates;
    private int numThreads;

    public LLP(int numThreads) {
        this.forbiddenStates = new ArrayList<>();
        this.numThreads = numThreads;
    }

    // Checks whether process j is forbidden in the state vector G
    public abstract boolean forbidden(int j);

    // Advances on process j
    public abstract void advance(int j);

    // For a given problem, instantiates the stateVector with starting values
    public abstract void initializeState();

    public void findForbidden() {
        forbiddenStates = new ArrayList<>();
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            final int temp = i;

            threads[i] = new Thread(() -> {
                if (forbidden(temp)) {
                    synchronized(this) {
                        forbiddenStates.add(temp);
                    }
                }
            });

            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void solve() {
        // Implement this method. There are many ways to do this but you
        // should follow the following basic steps:

        // 1. Compute the forbidden states
        // 2. Advance on forbidden states in parallel
        // 3. Repeat 1 and 2 until there are no forbidden states

        initializeState();
        findForbidden();

        while (forbiddenStates.size() != 0) {
            Thread[] threads = new Thread[forbiddenStates.size()];
            int index = 0;

            for (int i : forbiddenStates) {
                threads[index] = new Thread(() -> advance(i));
                threads[index].start();
                index += 1;
            }

            for (int i = 0; i < threads.length; i++) {
                try {
                    threads[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            findForbidden();
        }
    }
}
