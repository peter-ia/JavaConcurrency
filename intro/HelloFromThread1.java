// Peter Idestam-Almquist, 2017-01-10.

package peteria.javaconcurrency.intro;

class HelloFromThread1 extends Thread {
	public void run() {
		System.out.println("Hello from a thread!");
	}

	public static void main(String[] args) {
		HelloFromThread1 myThread = new HelloFromThread1();
		myThread.start();
    }
}
