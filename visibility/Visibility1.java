// Peter Idestam-Almquist, 2017-02-20.
// Sharing variables without synchronization.

package peteria.javaconcurrency.visibility;

class Visibility1 implements Runnable {
	private static boolean ok = false;
	private static int myInt = 0;

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
		new Thread(new Visibility1()).start();
		myInt = 21;
		ok = true;
    }
}
