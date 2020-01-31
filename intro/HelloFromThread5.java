// Peter Idestam-Almquist, 2017-01-10.

package peteria.javaconcurrency.intro;

class HelloFromThread5 implements Runnable {
	public void run() {
		System.out.println("Sleeping ...");
		try {
			Thread.sleep(1000); //Thread sleeps for 1 s.
		}
		catch (InterruptedException e) { 
			System.out.println(e);
		}
		System.out.println("Hello from a thread!");
	}
	
	public static void main(String[] args) {
		HelloFromThread5 hello = new HelloFromThread5();
        Thread myThread = new Thread(hello);
		try {
			myThread.start();
			myThread.join();
		}
		catch (InterruptedException e) {
			System.out.println(e);
		}
		System.out.println("Main thread finished!");
    }
}
