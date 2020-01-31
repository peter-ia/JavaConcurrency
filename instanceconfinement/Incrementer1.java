// Peter Idestam-Almquist, 2017-02-22.
// Escaping variable.

package peteria.javaconcurrency.instanceconfinement;

import peteria.javaconcurrency.annotation.NotThreadSafe;

@NotThreadSafe
class Incrementer1 implements Runnable {
	private IntHolder myInt = new IntHolder(0);
	
	public synchronized void increase() {
		myInt.set(myInt.get() + 1);
	} 
	
	public synchronized IntHolder get() {
		return myInt;
	} 
	
	public void run() {
		for (int i = 0; i < 100000; i++) {
			increase();
			Thread.yield();
		}
		System.out.println("Incrementer1.myInt = " + myInt.get());
	}
}
