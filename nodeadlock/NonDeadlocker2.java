// Peter Idestam-Almquist, 2017-02-16.
// Avoiding deadlock by timed lock attempts.

package peteria.javaconcurrency.nodeadlock;

import java.util.Random;
import peteria.javaconcurrency.annotation.ThreadSafe;
import peteria.javaconcurrency.annotation.GuardedBy;

@ThreadSafe
class NonDeadlocker2 implements Runnable {
	@GuardedBy("from")
	Account from;
	@GuardedBy("to")
	Account to;
	int amount;
	
	NonDeadlocker2(Account from, Account to, int amount) {
		this.from = from;
		this.to = to;
		this.amount = amount;
	}

	public void run() {
		transferMoney(from, to, amount);
	}
	
	public boolean transferMoney(Account from, Account to, int amount) {
		Random random = new Random();
		for (int i = 0; i < 100; i++) {
			System.out.println("Trying (from): " + from.id);
			if (from.lock.tryLock()) {
				System.out.println("Taken (from): " + from.id);
				try {
					Thread.yield(); // Impose thread switch.
					System.out.println("Trying (to): " + to.id);
					if (to.lock.tryLock()) {
						System.out.println("Taken (to): " + to.id);
						try {
							from.debit(amount);
							to.credit(amount);
							return true;
						}
						finally {
							to.lock.unlock();
							System.out.println("Released (to): " + to.id);
						}
					}
				}
				finally {
					from.lock.unlock();
					System.out.println("Released (from): " + from.id);
				}
			}
			try {
				Thread.sleep(random.nextInt(100)); // Random wait before retry.
			}
			catch (InterruptedException e) { 
				System.out.println(e);
			}
		}
		return false;
	}

	public static void main(String[] args) {
		Account account1 = new Account(1,1000);
		Account account2 = new Account(2,2000);
		NonDeadlocker2 locker1 = new NonDeadlocker2(account1, account2, 100);
		NonDeadlocker2 locker2 = new NonDeadlocker2(account2, account1, 200);
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
