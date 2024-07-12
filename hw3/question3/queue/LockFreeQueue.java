package queue;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class LockFreeQueue implements MyQueue {
  private AtomicReference<Node> head;
  private AtomicReference<Node> tail;

  public AtomicInteger size;

  public LockFreeQueue() {
    // Dummy Node
    Node sentinel = new Node(null);

    head = new AtomicReference<>(sentinel);
    tail = new AtomicReference<>(sentinel);

    size = new AtomicInteger(0);
  }

  public boolean enq(Integer value) {
    Node insert = new Node(value);

    while (true) {
      Node tailPtr = tail.get();
      Node next = tailPtr.next.get();

      if (tailPtr == tail.get()) {
        if (next == null) { // Is tail pointing to the last node?
          if ((tailPtr.next).compareAndSet(next, insert)) { // Link insert node
            tail.compareAndSet(tailPtr, insert);
            size.incrementAndGet();
            return true;
          }
        } else { // Swing tail to new tail
          tail.compareAndSet(tailPtr, next);
        }
      }
    }
  }
  
  public Integer deq() {
    while (true) {
      Node headPtr = head.get();
      Node tailPtr = tail.get();
      Node next = headPtr.next.get();

      if (headPtr == head.get()) {
        if (headPtr == tailPtr) {
          if (next == null) { // Empty Queue
            return null;
          } else { // Swing tail to new tail
            tail.compareAndSet(tailPtr, next);
          }
        } else { // popleft()
          Integer result = next.value;

          if (head.compareAndSet(headPtr, next)) {
            size.decrementAndGet();
            return result;
          }
        }
      }
    }
  }
  
  // Use AtomicReference for next - Sid's OH
  protected class Node {
	  public Integer value;
	  public AtomicReference<Node> next;
		    
	  public Node(Integer x) {
		  value = x;
		  next = new AtomicReference<>(null);
	  }
  }
}
