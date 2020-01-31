// Peter Idestam-Almquist, 2017-03-05.
// Thread-safe linked item holder based on immutability.
// Note that the items stored might not be thread-safe.

package peteria.javaconcurrency.immutability;

import peteria.javaconcurrency.annotation.ThreadSafe;

@ThreadSafe
class ImmutableLinkItem<T> {
	final T item;
	final ImmutableLinkItem<T> next;
	
	ImmutableLinkItem(T item, ImmutableLinkItem<T> next) {
		this.item = item;
		this.next = next;
	}
	
	ImmutableLinkItem<T> copy() {
		return new ImmutableLinkItem<T>(item, next);
	}
}
