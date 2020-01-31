// Peter Idestam-Almquist, 2017-02-16.
// Cooperating objects deadlock.

package peteria.javaconcurrency.liveness;

import peteria.javaconcurrency.annotation.ThreadSafe;
import peteria.javaconcurrency.annotation.GuardedBy;

@ThreadSafe
class Deadlocker3A implements Runnable {
	@GuardedBy("this")
	Deadlocker3B lockerB = new Deadlocker3B(this);

	synchronized void methodA1() {
		System.out.println("Taken lockerA in methodA1.");
		Thread.yield(); // Impose thread switch.
		System.out.println("Calling lockerB.methodB2 in methodA1.");
		lockerB.methodB2();
		System.out.println("Releasing lockerA in methodA1.");
	}
	
	synchronized void methodA2() {
		System.out.println("Taken lockerA in methodA2.");
		System.out.println("Releasing lockerA in methodA2.");
	}
	
	public void run() {
		methodA1();
	}

	public static void main(String[] args) {
		Deadlocker3A lockerA = new Deadlocker3A();
        Thread thread1 = new Thread(lockerA);
        Thread thread2 = new Thread(lockerA.lockerB);
		try { 
			thread1.start(); 
			thread2.start();
			thread1.join(); 
			thread2.join(); 
		}
		catch (InterruptedException e) { 
			System.out.println(e);
		}
		System.out.println("Done!");
    }
}
