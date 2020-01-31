// Peter Idestam-Almquist, 2017-02-20.
// Thread confinement by stack confinement.

package peteria.javaconcurrency.threadconfinement;

import peteria.javaconcurrency.annotation.ThreadSafe;

@ThreadSafe
class Incrementer2 implements Runnable {
	public void run() {
		int myInt = 0;
		for (int i = 0; i < 100000; i++) {
			myInt++;
		}
		System.out.println("myInt = " + myInt);
	}
	
	public static void main(String[] args) {
		Incrementer2 incrementer = new Incrementer2();
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
    }
}
