// Peter Idestam-Almquist, 2020-01-26.
// Thread confinement by ThreadLocal.

package peteria.javaconcurrency.threadconfinement;

import peteria.javaconcurrency.annotation.ThreadSafe;

@ThreadSafe
class Incrementer3 implements Runnable {
	private ThreadLocal<Integer> myIntHolder = 
		new ThreadLocal<Integer>() {
			protected Integer initialValue() {
				return 0;
			}
		};

	public void run() {
		for (int i = 0; i < 100000; i++) {
			myIntHolder.set(myIntHolder.get() + 1);
		}
		System.out.println("myInt = " + myIntHolder.get());
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
    }
}
