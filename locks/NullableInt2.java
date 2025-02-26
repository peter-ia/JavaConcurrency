// Peter Idestam-Almquist, 2025-02-25.
// The same lock.

package peteria.javaconcurrency.locks;

import java.util.Random;
import peteria.javaconcurrency.annotation.ThreadSafe;
import peteria.javaconcurrency.annotation.GuardedBy;

@ThreadSafe
class NullableInt2 implements Runnable {
	boolean test;
	
	@GuardedBy("lock")
	static private boolean isNull = true;

	@GuardedBy("lock")
	static int value = -1;

	static private Object lock = new Object();
	
	NullableInt2(boolean test) {
		this.test = test;
	}

	void setValue(int value) {
		synchronized (lock) {
			this.isNull = false;
			try {
				Thread.sleep(2);
			}
			catch (InterruptedException e) { 
				System.out.println(e);
			}
			this.value = value;
		}
	}

	void setToNull() {
		synchronized (lock) {
			this.isNull = true;
		}
	}

	boolean hasValue() {
		try {
			Thread.sleep(1);
		}
		catch (InterruptedException e) { 
			System.out.println(e);
		}
		synchronized (lock) {
			return !isNull;
		}
	}
	
	int getValue() {
		synchronized (lock) {
			if (isNull)
				throw new RuntimeException("Value is null.");
			return value;
		}
	}
	
	public void run() {
		if (test) {
			setValue(999);
		}
		else {
			if (hasValue())
				System.out.println(getValue());
		}
	}
	
	public static void main(String[] args) {
		NullableInt2 nullableInt1 = new NullableInt2(false);
		NullableInt2 nullableInt2 = new NullableInt2(true);
		
		Thread thread1 = new Thread(nullableInt1);
		Thread thread2 = new Thread(nullableInt2);

 		try { 

			thread1.start(); 
			thread2.start();
			thread1.join(); 
			thread2.join(); 
		}
		catch (InterruptedException e) { 
			System.out.println(e);
		}
   }
}
