package AbortLock;

public class AbortException extends Exception {
    public AbortException(String errorMessage){
        super(errorMessage);
    }
}