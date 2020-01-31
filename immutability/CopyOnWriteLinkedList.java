// Peter Idestam-Almquist, 2017-03-06.
// Thread-safe (shallowly) data structure based on immutability.
// Implemented using a lock (pessimistic concurrency control).

package peteria.javaconcurrency.immutability;

import peteria.javaconcurrency.annotation.ThreadSafe;
import peteria.javaconcurrency.annotation.GuardedBy;
import java.util.function.Consumer;

@ThreadSafe
class CopyOnWriteLinkedList<T> {
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
	
	T remove() {
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
		synchronized (lock) {
			if (first != null)
				first = copyAndAdd(first, newItem);
			else
				first = newItem;
		}
	}
	
	void forEach(Consumer<T> action) {
		ImmutableLinkItem<T> current = first;
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
