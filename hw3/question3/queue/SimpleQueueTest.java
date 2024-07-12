package queue;

import org.junit.Assert;
import org.junit.Test;

public class SimpleQueueTest {
    class ThreadQueue implements Runnable {
        private MyQueue queue;

        ThreadQueue(MyQueue queue) {
            this.queue = queue;
        }

        public void run() {
            queue.enq(0);
            queue.deq();
        }
    }

    @Test
    public void testLockQueueEmpty() {
        LockQueue queue = new LockQueue();
        Thread[] threads = new Thread[100000];

        for (int i = 0; i < 100000; i++) {
            threads[i] = new Thread(new ThreadQueue(queue));
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Assert.assertEquals(queue.size.get(), 0);
    }

    @Test
    public void testFreeQueueEmpty() {
        LockFreeQueue queue = new LockFreeQueue();
        Thread[] threads = new Thread[100000];

        for (int i = 0; i < 100000; i++) {
            threads[i] = new Thread(new ThreadQueue(queue));
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Assert.assertEquals(queue.size.get(), 0);
    }
}
