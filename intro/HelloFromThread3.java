// Peter Idestam-Almquist, 2017-01-10.

package peteria.javaconcurrency.intro;

class HelloFromThread3 implements Runnable {
	public void run() {
		System.out.println("Hello from a thread!");
		Thread.yield();
		System.out.println("Hello again from the thread!");
	}

	public static void main(String[] args) {
		HelloFromThread3 hello = new HelloFromThread3();
        Thread myThread = new Thread(hello);
		myThread.start();
    }
}
