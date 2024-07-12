package q1;

import java.util.concurrent.locks.*;

public class Monkey {
    private static ReentrantLock monitorLock = new ReentrantLock();
    private static Condition randomMonkey = monitorLock.newCondition();
    private static Condition kongMonkey = monitorLock.newCondition();

    private static int count = 0;
    private static int curDirection = -2; // -1, 0, 1, -2 (no direction)
    private static boolean kongWaiting = false;

    public void ClimbRope(int direction) throws InterruptedException {
        monitorLock.lock();
        
        try {
            // Kong
            if (direction == -1) {
                kongWaiting = true;
                while (count > 0)
                    kongMonkey.await();
                kongWaiting = false;
                count += 1;
                curDirection = -1;
            
            // Random
            } else {
                if (direction == 0) {
                    while (count == 3 || curDirection == 1 || curDirection == -1 || kongWaiting)
                        randomMonkey.await();
                    count += 1;
                    curDirection = 0;
                } else if (direction == 1) {
                    while (count == 3 || curDirection == 0 || curDirection == -1 || kongWaiting)
                        randomMonkey.await();
                    count += 1;
                    curDirection = 1;
                }
            }
        } finally {
            monitorLock.unlock();
        }
    }

    public void LeaveRope() {
        monitorLock.lock();
        
        try {
            count -= 1;

            if (count == 0) {
                curDirection = -2;
                kongMonkey.signal();
                randomMonkey.signalAll();
            }

        } finally {
            monitorLock.unlock();
        }
    }

    /**
     * Returns the number of monkeys on the rope currently for test purpose.
     *
     * @return the number of monkeys on the rope
     *
     * Positive Test Cases:
     * case 1: when normal monkey (0 and 1) is on the rope, this value should <= 3, >= 0
     * case 2: when Kong is on the rope, this value should be 1
     */
    public int getNumMonkeysOnRope() {
        return count;
    }

}
