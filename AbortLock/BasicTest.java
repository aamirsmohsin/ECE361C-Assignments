package AbortLock;

public class BasicTest {
    private int var1;
    private int var2;
    private AbortLock lock = new AbortLock(BasicTest.class);

    public BasicTest(int v1, int v2) {
        this.var1 = v1;
        this.var2 = v2;
    }

    public void printVariables() {
        System.out.println("---");
        System.out.println(var1);
        System.out.println(var2);
        System.out.println("---");
    }

    public void throwError() {
        lock.lock(this);
        printVariables();

        try {
            this.var1 = 2;
            this.var2 = 2 / 0;
        } catch (Exception e) {
            System.out.println("Error Detected");
            lock.abort(lock, this);
            printVariables();
        } finally {
            lock.unlock();
        }
    }
}
