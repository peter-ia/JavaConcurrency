// Peter Idestam-Almquist, 2017-01-24.
// The same lock.

package peteria.javaconcurrency.locks;

import peteria.javaconcurrency.annotation.ThreadSafe;
import peteria.javaconcurrency.annotation.GuardedBy;

@ThreadSafe
class Incrementer9 implements Runnable {
	@GuardedBy("lock")
	private int myInt1 = 0, myInt2 = 0;
	private Object lock = new Object();
	
	public void run() {
		for (int i = 0; i < 100000; i++) {
			synchronized (lock) {
				if ((myInt1 + myInt2) % 2 == 0)
					myInt1++;
				else
					myInt2++;
			}
		}
	}
	
	public static void main(String[] args) {
		Incrementer9 incrementer = new Incrementer9();
		Thread thread1 = new Thread(incrementer);
		Thread thread2 = new Thread(incrementer);
		try { 
			thread1.start(); 
			thread2.start();
			thread1.join(); 
			thread2.join(); 
		}
		catch (InterruptedException e) { 
			System.out.println(e);
		}
		System.out.println("myInt1 = " + incrementer.myInt1);
		System.out.println("myInt2 = " + incrementer.myInt2);
    }
}
