package question5.Bakery;

public interface Lock {
    public void lock(int pid);

    public void unlock(int pid);
}
