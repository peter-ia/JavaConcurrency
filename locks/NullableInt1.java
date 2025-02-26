// Peter Idestam-Almquist, 2025-02-25.
// Not the same lock.

package peteria.javaconcurrency.locks;

import java.util.Random;
import peteria.javaconcurrency.annotation.NotThreadSafe;
import peteria.javaconcurrency.annotation.GuardedBy;

@NotThreadSafe
class NullableInt1 implements Runnable {
	boolean test;
	
	@GuardedBy("nullLock")
	static private boolean isNull = true;

	@GuardedBy("valueLock")
	static int value = -1;

	static private Object nullLock = new Object();
	static private Object valueLock = new Object();
	
	NullableInt1(boolean test) {
		this.test = test;
	}

	void setValue(int value) {
		synchronized (nullLock) {
			this.isNull = false;
		}
		try {
			Thread.sleep(2);
		}
		catch (InterruptedException e) { 
			System.out.println(e);
		}
		synchronized (valueLock) {
			this.value = value;
		}
	}

	void setToNull() {
		synchronized (nullLock) {
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
		synchronized (nullLock) {
			return !isNull;
		}
	}
	
	int getValue() {
		synchronized (nullLock) {
			if (isNull)
				throw new RuntimeException("Value is null.");
		}
		synchronized (valueLock) {		
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
		NullableInt1 nullableInt1 = new NullableInt1(false);
		NullableInt1 nullableInt2 = new NullableInt1(true);
		
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
