// Peter Idestam-Almquist, 2017-02-22.
// Latch implementation using an internal lock object.

package peteria.javaconcurrency.synchronizers;

import peteria.javaconcurrency.annotation.ThreadSafe;

@ThreadSafe
class MyLatch {
	private int count;
	private Object lock = new Object();
	
	MyLatch(int count) {
		this.count = count;
	}
	
	void await() {
		synchronized (lock) {
			if (count > 0)
				try {
					lock.wait();
				}
				catch (InterruptedException exception) {
					System.out.println(exception);
				}
		}
	}
	
	void countDown() {
		synchronized (lock) {
			if (count > 0) {
				count--;
				if (count == 0)
					lock.notifyAll();
			}
		}
	}
	
	public static void main(String[] args) {
		MyLatch myLatch = new MyLatch(1);
		new Thread(new Runnable() { 
			public void run() {
				System.out.println("Thread1: Before await()");
				myLatch.await();
				System.out.println("Thread1: After await()");
			}
		}).start();
		new Thread(new Runnable() { 
			public void run() {
				System.out.println("Thread2: Before await()");
				myLatch.await();
				System.out.println("Thread2: After await()");
			}
		}).start();
		try {
			Thread.sleep(1000);
		}
		catch (InterruptedException exception) {
			System.out.println(exception);
		}
		System.out.println("Before countDown()");
		myLatch.countDown();
		System.out.println("After countDown()");
	}
}
