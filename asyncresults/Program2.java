// Peter Idestam-Almquist, 2017-03-02.
// CompletableFuture (common ForkJoinPool).

package peteria.javaconcurrency.asyncresults;

import java.util.concurrent.CompletableFuture;

class Program2 {
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
			System.out.println("Before any call.");
			
			// Non-blocking.
			CompletableFuture<String> future1 = 
				CompletableFuture.supplyAsync(() -> getHello());
			System.out.println("After first call.");
			
			// Non-blocking.
			CompletableFuture<String> future2 = 
				future1.thenApply((x) -> concatHello(x));
			System.out.println("After second call.");
			
			// Non-blocking.
			CompletableFuture<String> future3 = 
				future2.thenApply((x) -> concatHello(x));
			System.out.println("After third call.");
			
			// Non-blocking.
			CompletableFuture<Void> future4 = 
				future3.thenAccept(System.out::println);
			System.out.println("After last call.");
			
			// Blocking.
			future4.get();
			System.out.println("Finished.");
		}
		catch(Exception exception) {
			System.out.println(exception);
		}
	}
}
