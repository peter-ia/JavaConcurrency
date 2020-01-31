// Peter Idestam-Almquist, 2017-02-08.

package peteria.javaconcurrency.liveness;

import java.util.concurrent.locks.ReentrantLock;

class Account {
	final int id;
	int balance;
	ReentrantLock lock;
	
	Account(int id, int balance) {
		this.id = id;
		this.balance = balance;
		lock = new ReentrantLock();
	}

	void debit(int amount) {
		balance = balance - amount;
	}
	
	void credit(int amount) {
		balance = balance + amount;
	}
}
