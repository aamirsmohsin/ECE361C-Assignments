# FinalProject361C

BasicTest.java

The AbortLock was tested using a basic implementation (BasicTest.java) on a single thread in which a divide by zero operation invokes an exception and the abort function is called. In this case, the original state of the object calling the divide by zero function is preserved. 

TimeoutThread.java

Additionally, a more complex test case (TimeoutThread.java) involved 5 threads (one of which holds the lock for significantly longer than the others). In this test, each thread acquires the lock and increments a shared value. The thread that holds the lock for too long aborts, which causes the state to be restored and the increment to be reversed. This example can model use cases where a thread times out or unexpectedly terminates.

PIncrement.java/SimpleTest.java

Finally, the performance of AbortLock was tested by having N threads (ranging from 1 to 8) cumulatively count to 1,200,000. Each thread acquired the AbortLock and incremented the count 1,200,000 / N times. The same test was completed with a ReentrantLock. There is additional overhead added by using the abort functionality. This overhead scales with the number of instance variables that a class holds due to the reflection used to save the state. You can see this difference by running PIncrement with AbortLock in SimpleTest and observing runtime and then running PIncrement with ReentrantLock and observing runtime.
