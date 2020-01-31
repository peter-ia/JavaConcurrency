// Peter Idestam-Almquist, 2017-02-22.
// Latch implementation using an internal lock object.

package peteria.javaconcurrency.synchronizers;

import peteria.javaconcurrency.annotation.ThreadSafe;

@ThreadSafe
class MyBarrier {
	private final int parties;
	private int count = 0;
	private Object lock = new Object();
	
	MyBarrier(int parties) {
		this.parties = parties;
	}
	
	void await() {
		synchronized (lock) {
			count++;
			if (count == parties) {
				lock.notifyAll();
				count = 0;
			}
			else
				try {
					lock.wait();
				}
				catch (InterruptedException exception) {
					System.out.println(exception);
				}
		}
	}
	
	public static void main(String[] args) {
		MyBarrier myBarrier = new MyBarrier(2);
		new Thread(new Runnable() { 
			public void run() {
				System.out.println("Thread1: Before await()");
				myBarrier.await();
				System.out.println("Thread1: After await()");
			}
		}).start();
		try {
			Thread.sleep(1000);
		}
		catch (InterruptedException exception) {
			System.out.println(exception);
		}
		new Thread(new Runnable() { 
			public void run() {
				System.out.println("Thread2: Before await()");
				myBarrier.await();
				System.out.println("Thread2: After await()");
			}
		}).start();
	}
}
