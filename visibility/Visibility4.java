// Peter Idestam-Almquist, 2017-02-20.
// Sharing variables without synchronization.

package peteria.javaconcurrency.visibility;

class Visibility4 implements Runnable {
	private static int myInt = 0;

	public void run() {
		myInt = 21;
	}
	
	public static void main(String[] args) {
		Thread thread = new Thread(new Visibility4());
		thread.start(); 
		try {
			Thread.sleep(0);
		}
		catch (InterruptedException exception) {
			System.out.println(exception);
		}
		System.out.println(myInt);
    }
}
