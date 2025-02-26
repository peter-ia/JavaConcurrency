// Peter Idestam-Almquist, 2025-02-21.
// Read-write-lock.

package peteria.javaconcurrency.locks;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import peteria.javaconcurrency.annotation.ThreadSafe;
import peteria.javaconcurrency.annotation.GuardedBy;

class Incrementer8 implements Runnable {
	private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	@GuardedBy("readWriteLock")
	private int myInt = 0;
	
	public void run() {
		int sum = 0;
		for (int i = 0; i < 100000; i++) {
			if (i % 10 == 0) {
				try {
					readWriteLock.writeLock().lock();
					myInt++;
				}
				finally {
					readWriteLock.writeLock().unlock();
				}
			}
			try {
				readWriteLock.readLock().lock();
				sum = sum + myInt;
			}
			finally {
				readWriteLock.readLock().unlock();
			}
		}
		System.out.println("sum = " + sum);
	}
	
	public static void main(String[] args) {
		Incrementer8 incrementer = new Incrementer8();
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
		System.out.println("myInt = " + incrementer.myInt);
    }
}
