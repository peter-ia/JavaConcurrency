// Peter Idestam-Almquist, 2017-02-16.
// Dynamic lock ordering deadlock.

package peteria.javaconcurrency.nodeadlock;

import peteria.javaconcurrency.annotation.ThreadSafe;
import peteria.javaconcurrency.annotation.GuardedBy;

@ThreadSafe
class Deadlocker2 implements Runnable {
	@GuardedBy("from")
	Account from;
	@GuardedBy("to")
	Account to;
	int amount;
	
	Deadlocker2(Account from, Account to, int amount) {
		this.from = from;
		this.to = to;
		this.amount = amount;
	}

	public void run() {
		transferMoney(from, to, amount);
	}
	
	public void transferMoney(Account from, Account to, int amount) {
		synchronized (from) {
			System.out.println("Taken (from): " + from.id);
			Thread.yield(); // Impose thread switch.
			synchronized (to) {
				System.out.println("Taken (to): " + to.id);
				from.debit(amount);
				to.credit(amount);
			}
			System.out.println("Released (to): " + to.id);
		}
		System.out.println("Released (from): " + from.id);
	}

	public static void main(String[] args) {
		Account account1 = new Account(1,1000);
		Account account2 = new Account(2,2000);
		Deadlocker2 locker1 = new Deadlocker2(account1, account2, 100);
		Deadlocker2 locker2 = new Deadlocker2(account2, account1, 200);
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
