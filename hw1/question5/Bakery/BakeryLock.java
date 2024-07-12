package question5.Bakery;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BakeryLock implements Lock {
    private static int n;
    private AtomicBoolean[] choosing;
    public AtomicInteger[] number;

    public BakeryLock(int numThreads) {
        n = numThreads;
        this.choosing = new AtomicBoolean[n];
        this.number = new AtomicInteger[n];

        for (int i = 0; i < this.n; i++) {
            choosing[i] = new AtomicBoolean(false);
            number[i] = new AtomicInteger(0);
        }
    }

    public void lock(int pid) {
        choosing[pid].set(true);

        for (int j = 0; j < n; j++) {
            number[pid].set(Math.max(number[pid].get(), number[j].get()));
        }

        number[pid].set(number[pid].get() + 1);
        choosing[pid].set(false);

        for (int j = 0; j < this.n; j++) {
            while (choosing[j].get()) ;
            while ((number[j].get() != 0) && ((number[j].get() < number[pid].get()) || ((number[j].get() == number[pid].get()) && j < pid))) ;
        }
    }

    public void unlock(int pid) {
        number[pid].set(0);
    }
}
