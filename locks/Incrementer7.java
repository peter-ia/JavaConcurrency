// Peter Idestam-Almquist, 2017-01-24.
// Not the same lock.

package peteria.javaconcurrency.locks;

import peteria.javaconcurrency.annotation.NotThreadSafe;
import peteria.javaconcurrency.annotation.GuardedBy;

@NotThreadSafe
class Incrementer7 implements Runnable {
	@GuardedBy("this")
	private static int myInt = 0;
	
	private synchronized void increaseMyInt() { 
		myInt++;
	}
	
	public void run() {
		for (int i = 0; i < 100000; i++) {
			increaseMyInt();
		}
	}
	
	public static void main(String[] args) {
		Incrementer7 incrementer1 = new Incrementer7();
		Incrementer7 incrementer2 = new Incrementer7();
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
		System.out.println("myInt = " + myInt);
    }
}
