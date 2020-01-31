// Peter Idestam-Almquist, 2017-02-22.
// Catching escaping variable.

package peteria.javaconcurrency.instanceconfinement;

import peteria.javaconcurrency.annotation.NotThreadSafe;

@NotThreadSafe
class Incrementer2 implements Runnable {
	private IntHolder myInt;
	
	Incrementer2(IntHolder myInt) {
		this.myInt = myInt;
	}
	
	public synchronized void increase() {
		myInt.set(myInt.get() + 1);
	} 
	
	public void run() {
		for (int i = 0; i < 100000; i++) {
			increase();
			Thread.yield();
		}
		System.out.println("Incrementer2.myInt = " + myInt.get());
	}

	public static void main(String[] args) {
		Incrementer1 incrementer1 = new Incrementer1();
		Incrementer2 incrementer2 = new Incrementer2(incrementer1.get());
		Thread thread1 = new Thread(incrementer1);
		Thread thread2 = new Thread(incrementer2);
		try { 
			thread1.start(); 
			thread2.start();
			thread1.join(); 
			thread2.join(); 
		}
		catch (InterruptedException e) { 
			System.out.println(e);
		}
	}
}
