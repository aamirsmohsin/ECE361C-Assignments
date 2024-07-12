package stack;

import org.junit.Assert;
import org.junit.Test;

public class SimpleStackTest {
    class ThreadStack implements Runnable {
        private MyStack stack;

        ThreadStack(MyStack stack) {
            this.stack = stack;
        }

        public void run() {
            try {
                stack.push(0);
                stack.pop();
            } catch (EmptyStack s) {
                s.printStackTrace();
            }
        }
    }

    @Test
    public void testLockStackEmpty() {
        LockStack stack = new LockStack();
        Thread[] threads = new Thread[100000];

        for (int i = 0; i < 100000; i++) {
            threads[i] = new Thread(new ThreadStack(stack));
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Assert.assertEquals(stack.size, 0);
    }

    @Test
    public void testFreeStackEmpty() {
        LockFreeStack stack = new LockFreeStack();
        Thread[] threads = new Thread[100000];

        for (int i = 0; i < 100000; i++) {
            threads[i] = new Thread(new ThreadStack(stack));
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Assert.assertEquals(stack.size.get(), 0);
    }
}
