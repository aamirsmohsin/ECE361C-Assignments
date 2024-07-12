package stack;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockStack implements MyStack {
	private Node top = null;
	private Lock lock;
	public int size;
	
  public LockStack() {
	  lock = new ReentrantLock();
	  size = 0;
  }
  
  public boolean push(Integer value) {
	  lock.lock();

	  try {
		// Insert into the 'front' of the LL
		Node insert = new Node(value);
		insert.next = top;
		top = insert;

		size++;
		return true;
	  } finally {
		lock.unlock();
	  }
  }
  
  public Integer pop() throws EmptyStack {
	  lock.lock();

	  try {
		if (top == null) throw new EmptyStack();

		// Remove the 'front' of the LL
		Node remove = top;
		Node newTop = top.next;
		top = newTop;
		
		size--;
		return remove.value;
	  } finally {
		lock.unlock();
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
