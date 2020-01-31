// Peter Idestam-Almquist, 2017-02-16.
// Lock ordering deadlock.

package peteria.javaconcurrency.nodeadlock;

import peteria.javaconcurrency.annotation.ThreadSafe;
import peteria.javaconcurrency.annotation.GuardedBy;

@ThreadSafe
class Deadlocker1 implements Runnable {
	@GuardedBy("account1")
	Account account1;
	@GuardedBy("account2")
	Account account2;
	boolean inOneOrder;

	Deadlocker1(Account account1, Account account2, boolean inOneOrder) {
		this.account1 = account1;
		this.account2 = account2;
		this.inOneOrder = inOneOrder;
	}
	
	void lockInOneOrder() {
		synchronized (account1) {
			System.out.println("Taken (lockInOneOrder): " + account1.id);
			Thread.yield(); // Impose thread switch.
			synchronized (account2) {
				System.out.println("Taken (lockInOneOrder): " + account2.id);
			}
			System.out.println("Released (lockInOneOrder): " + account2.id);
		}
		System.out.println("Released (lockInOneOrder): " + account1.id);
	}

	void lockInAnotherOrder() {
		synchronized (account2) {
			System.out.println("Taken (lockInAnotherOrder): " + account2.id);
			Thread.yield(); // Impose thread switch.
			synchronized (account1) {
				System.out.println("Taken (lockInAnotherOrder): " + account1.id);
			}
			System.out.println("Released (lockInAnotherOrder): " + account1.id);
		}
		System.out.println("Released (lockInAnotherOrder): " + account2.id);
	}
	
	public void run() {
		if (inOneOrder)
			lockInOneOrder();
		else
			lockInAnotherOrder();
	}

	public static void main(String[] args) {
		Account account1 = new Account(1,1000);
		Account account2 = new Account(2,2000);
		Deadlocker1 locker1 = new Deadlocker1(account1, account2, true);
		Deadlocker1 locker2 = new Deadlocker1(account1, account2, false);
        Thread thread1 = new Thread(locker1);
        Thread thread2 = new Thread(locker2);
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
