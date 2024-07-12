package queue;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockQueue implements MyQueue {
  private Lock enqLock;
  private Lock deqLock;
  public AtomicInteger size;

  Node head;
  Node tail;

  public LockQueue() {
	  enqLock = new ReentrantLock();
    deqLock = new ReentrantLock();

    // Race conditions exist with two locks
    size = new AtomicInteger(0);

    // Create Dummy Node
    head = new Node(0);
    tail = head;
  }
  
  public boolean enq(Integer value) {
    enqLock.lock();

    try {
      Node insert = new Node(value);
      tail.next = insert;
      tail = insert;

      size.incrementAndGet();
      return true;
    } finally {
      enqLock.unlock();
    }
  }
  
  public Integer deq() {
    deqLock.lock();

    try {
      if (head.next == null) {
        return null;
      }

      Integer result = head.next.value;
      head = head.next;
      size.decrementAndGet();

      return result;
    } finally {
      deqLock.unlock();
    }
  }
  
  protected class Node {
	  public Integer value;
	  public Node next;
		    
	  public Node(Integer x) {
		  value = x;
		  next = null;
	  }
  }
}
