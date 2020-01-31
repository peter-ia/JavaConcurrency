// Peter Idestam-Almquist, 2017-01-24.
// No concurrency.

package peteria.javaconcurrency.locks;

import peteria.javaconcurrency.annotation.NotThreadSafe;

@NotThreadSafe
class Incrementer1 implements Runnable {
	private int myInt = 0;
	
	public void run() {
		for (int i = 0; i < 100000; i++) { 
			myInt++; 
		}
	}
	
	public static void main(String[] args) {
		Incrementer1 incrementer = new Incrementer1();
		Thread thread1 = new Thread(incrementer);
		Thread thread2 = new Thread(incrementer);
		try {
			thread1.start(); 
			thread1.join();
			thread2.start(); 
			thread2.join();
		}
		catch (InterruptedException e) { 
			System.out.println(e);
		}
		System.out.println("myInt = " + incrementer.myInt);
    }
}
