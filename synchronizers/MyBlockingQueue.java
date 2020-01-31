// Peter Idestam-Almquist, 2017-02-22.
// Blocking queue implementation using semaphores.

package peteria.javaconcurrency.synchronizers;

import java.util.Queue;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import peteria.javaconcurrency.annotation.ThreadSafe;
import peteria.javaconcurrency.annotation.GuardedBy;

@ThreadSafe
class MyBlockingQueue<E> {
	private Object lock = new Object();
	@GuardedBy("lock")
	private Queue<E> items = new LinkedList<E>();
	private int capacity;
	private Semaphore takeSemaphore;
	private Semaphore putSemaphore;
	
	MyBlockingQueue(int capacity) {
		this.capacity = capacity;
		takeSemaphore = new Semaphore(capacity);
		putSemaphore = new Semaphore(capacity);
		try {
			takeSemaphore.acquire(capacity);
		}
		catch (InterruptedException exception) {
			System.out.println(exception);
		}
	}
	
	E take() {
		E item = null;
		try {
			takeSemaphore.acquire();
		}
		catch (InterruptedException exception) {
			System.out.println(exception);
		}
		synchronized (lock) {
			item = items.remove();
		}
		putSemaphore.release();
		return item;
	}
	
	void put(E item) {
		try {
			putSemaphore.acquire();
		}
		catch (InterruptedException exception) {
			System.out.println(exception);
		}
		synchronized (lock) {
			items.add(item);
		}
		takeSemaphore.release();
	}
}
