package AbortLock;

public class TimeoutThread {
    private static AbortLock lock = new AbortLock(TimeoutThread.class);
    private int val;
    private int MAX_TIME;

    public TimeoutThread(int val, int MAX_TIME) {
        this.val = val;
        this.MAX_TIME = MAX_TIME;
    }

    @SuppressWarnings("deprecation")
    public void doSomething(int length) {
        lock.lock(this);

        try {
            long startTime = System.currentTimeMillis();
            System.out.println("Thread " + Thread.currentThread().getId() + " has lock.");
            System.out.println("Initial value: " + val);

            val += 1;
            Thread.sleep(length);

            long elapsedTime = System.currentTimeMillis() - startTime;

            System.out.print("Thread held lock for: " + elapsedTime);
		    System.out.println(" milliseconds");

            if (elapsedTime > MAX_TIME) {
                throw new AbortException("Abort detected");
            }

        } catch (AbortException e) {
            lock.abort(lock, this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println("Lock unlocked");
            System.out.println("Final value: " + val);
            System.out.println("************");
        }
    }
}
