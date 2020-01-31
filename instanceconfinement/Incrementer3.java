// Peter Idestam-Almquist, 2017-02-22.

package peteria.javaconcurrency.instanceconfinement;

import peteria.javaconcurrency.annotation.ThreadSafe;

@ThreadSafe
class Incrementer3 implements Runnable {
	private Counter1 counter = new Counter1();
	
	public void run() {
		for (int i = 0; i < 100000; i++) {
			counter.increaseValue();
		}
	}
	
	public static void main(String[] args) {
		Incrementer3 incrementer = new Incrementer3();
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
		System.out.println("counter = " + incrementer.counter.getValue());
    }
}
