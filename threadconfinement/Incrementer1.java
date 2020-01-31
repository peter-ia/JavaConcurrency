// Peter Idestam-Almquist, 2020-01-26.
// Thread confinement.

package peteria.javaconcurrency.threadconfinement;

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
		Incrementer1 incrementer1 = new Incrementer1();
		Incrementer1 incrementer2 = new Incrementer1();
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
		System.out.println("myInt1 = " + incrementer1.myInt);    
		System.out.println("myInt2 = " + incrementer2.myInt);    
	}
}
