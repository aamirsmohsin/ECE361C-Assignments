/*
 * 
 * 1] Best practice for code structure on these questions?
 * 2] Works but takes really long, is that supposed to happen with Lamport's?
 * 3] Q3 on HW1
 * 
 */

package question5.Bakery;

public class PIncrement implements Runnable {
    private int numIncrements;
    private static BakeryLock lock;
    private int pid;

    // Shared Memory
    private static int resultC;

    public PIncrement(int numIncrements, BakeryLock lock, int pid) {
        this.numIncrements = numIncrements;
        this.lock = lock;
        this.pid = pid;
    }

    public static int parallelIncrement(int c, int numThreads) {
        int numIncrements = 1200000 / numThreads;
        BakeryLock lock = new BakeryLock(numThreads);

        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(new PIncrement(numIncrements, lock, i));
            threads[i].start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultC;
    }

    public void run() {
        for (int i = 0; i < numIncrements; i++) {
            lock.lock(this.pid);
            resultC++;
            lock.unlock(this.pid);
        }
    }
}
