// Peter Idestam-Almquist, 2017-03-05.

package peteria.javaconcurrency.immutability;

import peteria.javaconcurrency.annotation.NotThreadSafe;
import java.util.List;
import java.util.ArrayList;

// Escaping this reference.
@NotThreadSafe
class IntegerHolder {
	public final int myInt; // Immutable.
	
	IntegerHolder(List<IntegerHolder> holderList) {
		holderList.add(this);
		try {
			Thread.sleep(1); // Impose thread switch.
		}
		catch (InterruptedException exception) {
			System.out.println(exception);
		}		
		myInt = 21;
	}
	
	public static void main(String[] args) {
		List<IntegerHolder> holderList = new ArrayList<IntegerHolder>();
		Thread thread = new Thread(new IntegerCatcher(holderList));
		try {
			thread.start();
			IntegerHolder holder = new IntegerHolder(holderList);
			thread.join();
			System.out.println("IntegerHolder: myInt = " + holder.myInt);
		}
		catch (Exception exception) { 
			System.out.println(exception);
		}
	}
}
