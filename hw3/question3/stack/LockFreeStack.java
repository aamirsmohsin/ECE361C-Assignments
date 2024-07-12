package stack;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class LockFreeStack implements MyStack {
	private AtomicReference<Node> top;
	public AtomicInteger size;
	
  public LockFreeStack() {
	top = new AtomicReference<>(null);
	size = new AtomicInteger(0);
  }
	
  public boolean push(Integer value) {
	  Node node = new Node(value);

	  while (true) {

		// Insert node to the front of the LL
		Node prev = top.get();
		node.next = prev;
		
		// If consistent top, update the top to be the new node
		if (top.compareAndSet(prev, node)) {
			size.incrementAndGet();
			return true;
		} else {
			Thread.yield();
		}
	  }
  }
  
  public Integer pop() throws EmptyStack {
	  while (true) {
		Node current = top.get();

		if (current == null) throw new EmptyStack();

		Node prev = current.next;
		int result = current.value;

		// If consistent top, update the top to be the prevTop
		if (top.compareAndSet(current, prev)) {
			size.decrementAndGet();
			return result;
		} else {
			Thread.yield();
		}
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
