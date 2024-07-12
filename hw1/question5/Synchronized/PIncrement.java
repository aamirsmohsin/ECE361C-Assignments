package question5.Synchronized;

public class PIncrement implements Runnable {
    private static int numIncrements;
    private static int resultC;
    private static Object temp = new Object();
    
    public static int parallelIncrement(int c, int numThreads) {
        numIncrements = 1200000 / numThreads;

        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(new PIncrement());
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
            synchronized (temp) {
                resultC++;
            }
        }
    }
}
