// Peter Idestam-Almquist, 2017-02-20.
// Sharing variables without synchronization.

package peteria.javaconcurrency.visibility;

class Visibility5 implements Runnable {
	private static int myInt = 0;

	public void run() {
		myInt = 21;
	}
	
	public static void main(String[] args) {
		Thread thread = new Thread(new Visibility5());
		thread.start(); 
		try {
			Thread.sleep(1);
		}
		catch (InterruptedException exception) {
			System.out.println(exception);
		}
		System.out.println(myInt);
    }
}
