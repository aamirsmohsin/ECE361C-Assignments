package AbortLock;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Basic Test");
        BasicTest test = new BasicTest(4,3);
        test.throwError();
        System.out.println("-----------");

        System.out.println("Timeout Test");
        TimeoutThread test2 = new TimeoutThread(0, 4000);
        
        for (int i = 0; i < 5; i++) {
            if(i == 3) {
                Thread thread = new Thread(() -> {
                    test2.doSomething(5000);
                });
                thread.start();
            } else {
                Thread thread = new Thread(()->{
                    test2.doSomething(500);
                });
                thread.start();
            }
        }
        System.out.println("-----------");
    }
}
