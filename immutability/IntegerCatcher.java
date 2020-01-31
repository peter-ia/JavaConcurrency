// Peter Idestam-Almquist, 2017-03-05.

package peteria.javaconcurrency.immutability;

import java.util.List;

// Catching escaping this reference.
class IntegerCatcher implements Runnable {
	List<IntegerHolder> holderList;
	
	IntegerCatcher(List<IntegerHolder> holderList) {
		this.holderList = holderList;
	}
	
	public void run() {
		while (holderList.size() < 1)
			Thread.yield(); // Impose thread switch.
		System.out.println("IntegerCatcher: myInt = " + holderList.get(0).myInt);
	}
}
