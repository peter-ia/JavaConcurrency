// Peter Idestam-Almquist, 2017-02-16.
// Lock striping and avoiding hot field.

package peteria.javaconcurrency.performance;

import peteria.javaconcurrency.annotation.ThreadSafe;
import peteria.javaconcurrency.annotation.GuardedBy;

@ThreadSafe
class NumberCollection3 {
	final int PARTITIONS = 10;
	final int MAX = 1000;
	Object[] locks = new Object[PARTITIONS];
	@GuardedBy("locks")
	private long[][] numbers = new long[PARTITIONS][MAX];
	@GuardedBy("locks")
	int[] sizes = new int[PARTITIONS];
	
	NumberCollection3() {
		for (int i = 0; i < PARTITIONS; i++) {
			locks[i] = new Object();
		}
	}

	boolean add(long number) {
		int partition = (int)(number % 10);
		synchronized (locks[partition]) {
			if (sizes[partition] < MAX) {
				sizes[partition]++;
				numbers[partition][sizes[partition]] = number;
				return true;
			}
			else 
				return false;
		}
	}

	// Returns only approximate size.
	int size() {
		int size = 0;
		for (int i = 0; i < PARTITIONS; i++)
			size += sizes[i];
		return size;
	}
}
