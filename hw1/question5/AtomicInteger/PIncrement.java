package question5.AtomicInteger;

import java.util.concurrent.atomic.AtomicInteger;

public class PIncrement implements Runnable {
    private static int numIncrements;
    private static int resultC;
    private static AtomicInteger lock = new AtomicInteger(0);

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
            lock();
            resultC++;
            unlock();
        }
    }

    public void lock() {
        while (true) {
            while (lock.get() == 1) {}

            if (lock.compareAndSet(0, 1)) return;
        }
    }

    public void unlock() {
        lock.set(0);
    }
}