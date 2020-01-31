// Peter Idestam-Almquist, 2017-03-06.
// Thread-safe (shallowly) data structure based on immutability.
// Implemented using an AtomicReference (optimistic concurrency control).

package peteria.javaconcurrency.nonblocking;

import peteria.javaconcurrency.annotation.ThreadSafe;
import java.util.function.Consumer;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
class CopyOnWriteStack<T> {
	private final int MAX_TRIES = 100;
	private AtomicReference<ImmutableLinkItem<T>> atomicFirst = 
		new AtomicReference<ImmutableLinkItem<T>>();

	T peek() {
		ImmutableLinkItem<T> first = atomicFirst.get();
		if (first != null)
			return first.item;
		else
			return null;
	}
	
	T pop() {
		int round = 0;
		ImmutableLinkItem<T> first = null;
		while (round < MAX_TRIES) {
			first = atomicFirst.get();
			if (first == null)
				return null;
			if (atomicFirst.compareAndSet(first, first.next))
				return first.item;
			round++;
		}
		throw new RuntimeException("Failed to pop item.");
	}
	
	void push(T item) {
		int round = 0;
		ImmutableLinkItem<T> first = null;
		while (round < MAX_TRIES) {
			first = atomicFirst.get();
			ImmutableLinkItem<T> newItem = new ImmutableLinkItem<T>(item, first);
			if (atomicFirst.compareAndSet(first, newItem))
				return;
			round++;
		}
		throw new RuntimeException("Failed to push item.");
	}
	
	void forEach(Consumer<T> action) {
		ImmutableLinkItem<T> current = atomicFirst.get();
		while (current != null) {
			action.accept(current.item);
			current = current.next;
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
