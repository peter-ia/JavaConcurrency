// Peter Idestam-Almquist, 2017-03-05.
// Thread-safe (shallowly) data structure based on immutability.
// Implemented using a lock (pessimistic concurrency control).

package peteria.javaconcurrency.immutability;

import peteria.javaconcurrency.annotation.ThreadSafe;
import peteria.javaconcurrency.annotation.GuardedBy;
import java.util.function.Consumer;

@ThreadSafe
class CopyOnWriteStack<T> {
	private Object lock = new Object();
	@GuardedBy("lock")
	volatile private ImmutableLinkItem<T> first = null;

	T peek() {
		ImmutableLinkItem<T> temp = first;
		if (temp != null)
			return temp.item;
		else
			return null;
	}
	
	T pop() {
		ImmutableLinkItem<T> temp = null;
		synchronized (lock) {
			if (first != null) {
				temp = first;
				first = first.next;
			}
		}
		if (temp != null)
			return temp.item;
		else
			return null;
	}
	
	void push(T item) {
		ImmutableLinkItem<T> temp;
		synchronized (lock)  {
			temp = new ImmutableLinkItem<T>(item, first);
			first = temp;
		}
	}
	
	void forEach(Consumer<T> action) {
		ImmutableLinkItem<T> temp = first;
		while (temp != null) {
			action.accept(temp.item);
			temp = temp.next;
		}
	}
	
	public static void main(String[] args) {
		CopyOnWriteStack<String> stack = new CopyOnWriteStack<String>();
		stack.push("Hello");
		System.out.println(stack.peek());
		stack.push("Hello again");
		System.out.println(stack.peek());
		String temp = stack.pop();
		stack.push("Goodbye");
		stack.forEach((x) -> System.out.println(x));
	}
}
