// Peter Idestam-Almquist, 2017-02-16.
// Lock splitting.

package peteria.javaconcurrency.performance;

import peteria.javaconcurrency.annotation.ThreadSafe;
import peteria.javaconcurrency.annotation.GuardedBy;

@ThreadSafe
class NumberCollection2 {
	final int MAX = 10000;
	Object lockLong = new Object();
	Object lockDouble = new Object();
	@GuardedBy("lockLong")
	private long[] numbersLong = new long[MAX];
	@GuardedBy("lockDouble")
	private double[] numbersDouble = new double[MAX];
	@GuardedBy("lockLong")
	int sizeLong = 0;
	@GuardedBy("lockDouble")
	int sizeDouble = 0;

	boolean addLong(long number) {
		synchronized(lockLong) {
			if (sizeLong < MAX) {
				sizeLong++;
				numbersLong[sizeLong] = number;
				return true;
			}
			else 
				return false;
		}
	}
	
	boolean addDouble(double number) {
		synchronized(lockLong) {
			if (sizeDouble < MAX) {
				sizeDouble++;
				numbersDouble[sizeDouble] = number;
				return true;
			}
			else 
				return false;
		}
	}
	
	int sizeLong() {
		synchronized(lockLong) {
			return sizeLong;
		}
	}
	
	int sizeDouble() {
		synchronized(lockDouble) {
			return sizeDouble;
		}
	}
}
