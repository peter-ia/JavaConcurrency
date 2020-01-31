// Peter Idestam-Almquist, 2017-01-20.
// Synchronized method.

package peteria.javaconcurrency.safety;

class Incrementer4 implements Runnable {
	private int myInt = 0;
	
	private synchronized void increaseMyInt() {
		myInt++;
	}
	
	public void run() {
		for (int i = 0; i < 100000; i++) {
			increaseMyInt();
		}
	}
	
	public static void main(String[] args) {
		Incrementer4 incrementer = new Incrementer4();
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
