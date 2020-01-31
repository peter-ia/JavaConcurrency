// Peter Idestam-Almquist, 2017-02-06.
// Cooperating objects deadlock.

package peteria.javaconcurrency.nodeadlock;

import peteria.javaconcurrency.annotation.ThreadSafe;
import peteria.javaconcurrency.annotation.GuardedBy;

@ThreadSafe
class Deadlocker3B implements Runnable {
	@GuardedBy("this")
	Deadlocker3A lockerA;

	Deadlocker3B(Deadlocker3A objA) {
		lockerA = objA;
	}
	
	synchronized void methodB1() {
		System.out.println("Taken lockerB in methodB1.");
		Thread.yield(); // Impose thread switch.
		System.out.println("Calling lockerA.methodA2 in methodB1.");
		lockerA.methodA2();
		System.out.println("Releasing lockerB in methodB1.");
	}
	
	synchronized void methodB2() {
		System.out.println("Taken Deadlocker3B in methodB2.");
		System.out.println("Releasing Deadlocker3B in methodB2.");
	}
	
	public void run() {
		methodB1();
	}
}
