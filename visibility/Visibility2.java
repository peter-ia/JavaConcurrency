// Peter Idestam-Almquist, 2017-02-20.
// Sharing variables with synchronization.

package peteria.javaconcurrency.visibility;

import peteria.javaconcurrency.annotation.GuardedBy;

class Visibility2 implements Runnable {
	@GuardedBy("lock")
	private static boolean ok;
	@GuardedBy("lock")
	private static int myInt;
	private static Object lock = new Object();

	public void run() {
		while (true) {
			synchronized (lock) {
				if (ok) {
					System.out.println(myInt);
					return;
				}
				else
					Thread.yield();
			}
		}
	}
	
	public static void main(String[] args) {
		new Thread(new Visibility2()).start();
		synchronized (lock) {
			myInt = 21;
			ok = true;
		}
    }
}
