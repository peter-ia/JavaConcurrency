// Peter Idestam-Almquist, 2017-01-20.
// Synchronized block.

package peteria.javaconcurrency.safety;

class Incrementer3 implements Runnable {
	private int myInt = 0;
	
	public void run() {
		for (int i = 0; i < 100000; i++) {
			synchronized (this) {
				myInt++;
			}
		}
	}
	
	public static void main(String[] args) {
		Incrementer3 incrementer = new Incrementer3();
		Thread thread1 = new Thread(incrementer);
		Thread thread2 = new Thread(incrementer);
		try { 
			thread1.start(); 
			thread2.start();
			thread1.join(); 
			thread2.join(); 
		}
		catch (InterruptedException e) { 
			System.out.println(e);
		}
		System.out.println("myInt = " + incrementer.myInt);
    }
}
