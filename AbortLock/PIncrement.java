package AbortLock;

// import java.util.concurrent.locks.ReentrantLock;

// public class PIncrement implements Runnable {
//     private static int numIncrements;
//     private static int resultC;
//     private static ReentrantLock lock = new ReentrantLock();

//     public static int parallelIncrement(int c, int numThreads) {
//         numIncrements = 1200000 / numThreads;

//         Thread[] threads = new Thread[numThreads];

//         for (int i = 0; i < numThreads; i++) {
//             threads[i] = new Thread(new PIncrement());
//             threads[i].start();
//         }

//         try {
//             for (Thread thread : threads) {
//                 thread.join();
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//         }

//         return resultC;
//     }

//     public void run() {
//         for (int i = 0; i < numIncrements; i++) {
//             lock.lock();
//             try {
//                 resultC++;
//             } finally {
//                 lock.unlock();
//             }
//         }
//     }
// }

public class PIncrement implements Runnable {
    private static int numIncrements;
    private static int resultC;
    private static AbortLock lock = new AbortLock(PIncrement.class);

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
            lock.lock(this);
            try {
                resultC++;
            } finally {
                lock.unlock();
            }
        }
    }
}
