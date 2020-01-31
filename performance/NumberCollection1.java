// Peter Idestam-Almquist, 2017-02-16.
// High lock contention.

package peteria.javaconcurrency.performance;

import peteria.javaconcurrency.annotation.ThreadSafe;
import peteria.javaconcurrency.annotation.GuardedBy;

@ThreadSafe
class NumberCollection1 {
	final int MAX = 10000;
	@GuardedBy("this")
	private long[] numbersLong = new long[MAX];
	@GuardedBy("this")
	private double[] numbersDouble = new double[MAX];
	@GuardedBy("this")
	int sizeLong = 0;
	@GuardedBy("this")
	int sizeDouble = 0;

	synchronized boolean addLong(long number) {
		if (sizeLong < MAX) {
			sizeLong++;
			numbersLong[sizeLong] = number;
			return true;
		}
		else 
			return false;
	}
	
	synchronized boolean addDouble(double number) {
		if (sizeDouble < MAX) {
			sizeDouble++;
			numbersDouble[sizeDouble] = number;
			return true;
		}
		else 
			return false;
	}
	
	synchronized int sizeLong() {
		return sizeLong;
	}
	
	synchronized int sizeDouble() {
		return sizeDouble;
	}
}
