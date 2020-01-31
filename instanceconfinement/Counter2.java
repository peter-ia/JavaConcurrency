// Peter Idestam-Almquist, 2017-02-22.
// Monitor pattern variant.

package peteria.javaconcurrency.instanceconfinement;

import peteria.javaconcurrency.annotation.ThreadSafe;

@ThreadSafe
class Counter2 {
	private int myInt = 0;
	private Object lock = new Object();
	
	public void increaseValue() {
		synchronized (lock) {
			myInt++;
		}
	}
	
	public int getValue() {
		synchronized (lock) {
			return myInt;
		}
	}
}
