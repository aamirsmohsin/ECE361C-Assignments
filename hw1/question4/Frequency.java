package question4;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class Frequency implements Callable<Integer> {
    private int index;
    private int divSize;
    private int target;
    private int[] array;

    public Frequency(int index, int divSize, int target, int[] array) {
        this.index = index;
        this.divSize = divSize;
        this.target = target;
        this.array = array;
    }

    public static int parallelFreq(int x, int[] A, int numThreads) {
        if (A == null || numThreads <= 0 || A.length == 0) {
            return -1;
        }

        FutureTask<Integer>[] results = new FutureTask[numThreads];
        int divSize = (int) Math.ceil((double) A.length / numThreads);

        // Start the Threads
        for (int i = 0; i < numThreads; i++) {
            Frequency freq = new Frequency(i * divSize, divSize, x, A);
            FutureTask<Integer> futureTask = new FutureTask<>(freq);
            results[i] = futureTask;
            Thread thread = new Thread(futureTask);
            thread.start();
        }

        int res = 0;
        for (FutureTask<Integer> result : results) {
            try {
                res += result.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return res;
    }

    public Integer call() throws Exception {
        int start = this.index;
        int end = Math.min(start + this.divSize, this.array.length);
        int res = 0;

        System.out.println(start + " " + end);

        for (int i = start; i < end; i++) {
            if (this.array[i] == this.target) res += 1;
        }

        return res;
    }
}