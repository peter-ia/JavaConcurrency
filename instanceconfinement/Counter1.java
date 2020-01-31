// Peter Idestam-Almquist, 2017-02-22.
// Monitor pattern.

package peteria.javaconcurrency.instanceconfinement;

import peteria.javaconcurrency.annotation.ThreadSafe;

@ThreadSafe
class Counter1 {
	private int myInt = 0;
	
	synchronized void increaseValue() {
		myInt++;
	}
	
	synchronized int getValue() {
		return myInt;
	}
}
