// Peter Idestam-Almquist, 2017-02-20.
// Volatile variables.

package peteria.javaconcurrency.visibility;

class Visibility3 implements Runnable {
	private static volatile boolean ok;
	private static volatile int myInt;

	public void run() {
		while (true) {
			if (ok) {
				System.out.println(myInt);
				return;
			}
			else
				Thread.yield();
		}
	}
	
	public static void main(String[] args) {
		new Thread(new Visibility3()).start();
		myInt = 21;
		ok = true;
    }
}
