// Peter Idestam-Almquist, 2017-03-02.

package peteria.javaconcurrency.asyncresults;

import java.util.concurrent.Future;
import java.util.concurrent.Callable;

class Program1 implements Callable<String> {
	public String call() {
		try {
			Thread.sleep(1000);
		}
		catch (InterruptedException exception) {
			System.out.println(exception);
		}
		return "Hello!";
	}
	
	public static void main(String[] args) {
		Future<String> result = new AsyncResult<String>(new Program1());
		while (!result.isDone()) {
			System.out.println("Do some work in between.");
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException exception) {
				System.out.println(exception);
			}
		}
		try {
			System.out.println("Result = " + result.get());
		}
		catch (Exception exception) {
			System.out.println(exception);
		}
	}
}
