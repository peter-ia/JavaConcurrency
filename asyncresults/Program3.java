// Peter Idestam-Almquist, 2017-03-02.
// CompletableFuture (common ForkJoinPool).

package peteria.javaconcurrency.asyncresults;

import java.util.concurrent.CompletableFuture;

class Program3 {
	// Blocking.
	private static String getHello() {
		try {
			Thread.sleep(1000);
		}
		catch(InterruptedException exception) {
			System.out.println(exception);
		}	
		return "Hello";
	}
	
	// Blocking.
	private static String concatHello(String str) {
		try {
			Thread.sleep(1000);
		}
		catch(InterruptedException exception) {
			System.out.println(exception);
		}	
		return str + "--Hello";
	}
	
	public static void main(String[] args) {
		try {
			// Non-blocking.
			CompletableFuture<Void> future = 
				CompletableFuture
				.supplyAsync(Program3::getHello)
				.thenApply(Program3::concatHello)
				.thenApply(Program3::concatHello)
				.thenAccept(System.out::println);
			
			// Blocking.
			future.get();
		}
		catch(Exception exception) {
			System.out.println(exception);
		}
	}
}
