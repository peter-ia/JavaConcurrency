// Peter Idestam-Almquist, 2017-03-06.
// Thread-safe (shallowly) data structure based on immutability.
// Implemented using an AtomicReference (optimistic concurrency control).

package peteria.javaconcurrency.nonblocking;

import peteria.javaconcurrency.annotation.ThreadSafe;
import java.util.function.Consumer;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
class CopyOnWriteLinkedList<T> {
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
	
	T remove() {
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
		throw new RuntimeException("Failed to remove item.");
	}
	
	private ImmutableLinkItem<T> copyAndAdd(
		ImmutableLinkItem<T> current, 
		ImmutableLinkItem<T> last
	) {
		if (current.next == null) {
			return new ImmutableLinkItem<T>(current.item, last);
		}
		else {
			ImmutableLinkItem<T> next = copyAndAdd(current.next, last);
			return new ImmutableLinkItem<T>(current.item, next);
		}
	}
	
	void add(T item) {
		ImmutableLinkItem<T> newItem = new ImmutableLinkItem<T>(item, null);
		int round = 0;
		ImmutableLinkItem<T> first = null;
		ImmutableLinkItem<T> newFirst = null;
		while (round < MAX_TRIES) {
			first = atomicFirst.get();
			if (first == null) {
				if (atomicFirst.compareAndSet(first, newItem))
					return;
			}
			else {
				newFirst = copyAndAdd(first, newItem);
				if (atomicFirst.compareAndSet(first, newFirst))
					return;
			}
			round++;
		}
		throw new RuntimeException("Failed to add item.");
	}
	
	void forEach(Consumer<T> action) {
		ImmutableLinkItem<T> current = atomicFirst.get();
		while (current != null) {
			action.accept(current.item);
			current = current.next;
		}
	}
	
	public static void main(String[] args) {
		CopyOnWriteLinkedList<String> list = new CopyOnWriteLinkedList<String>();
		list.add("Hello");
		System.out.println(list.peek());
		list.add("Hello again");
		System.out.println(list.peek());
		String temp = list.remove();
		list.add("Goodbye");
		list.forEach((x) -> System.out.println(x));
	}
}
