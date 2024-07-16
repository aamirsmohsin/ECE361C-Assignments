# AbortLock

In cases of deadlocks, stale data, or thread terminations, multicore systems are prone to failure. The goal of this project is to design a lock that includes an abort method that can be called in erroneous cases. In these cases, the thread reverts the object that is using the lock to its prior state, leaving the system functional.
