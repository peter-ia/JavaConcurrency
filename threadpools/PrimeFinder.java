// Peter Idestam-Almquist, 2020-01-30.
// Example of using a thread pool.

package peteria.javaconcurrency.threadpools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

class PrimeFinder implements Callable<Integer> {
	private final static long MIN = 1;
	private final static long MAX = 2000000;
	
	private long min;
	private long max;
	private int step;
	private int counter;
	
	PrimeFinder(long min, long max, int step) {
		this.min = min;
		this.max = max;
		this.step = step;
		counter = 0;
	}
	
	// Inefficient implementation of isPrime on purpose.
	static boolean isPrime(long number) {
		boolean result = true;
		for (long d = 2; d <= Math.sqrt(number); d++) {
			if (number % d == 0)
				result = false; 
		}
		return result;
	}
	
	public Integer call() {
		for (long number = min; number <= max; number = number + step) {
			if (isPrime(number))
				counter++;
		}
		return counter;
	}
	
	public static void main(String[] args) {
		try {
			// Read input.
			InputStreamReader streamReader = new InputStreamReader(System.in);
			BufferedReader consoleReader = new BufferedReader(streamReader);
			System.out.print("Input (numThreads)>");
			String input = consoleReader.readLine();
			int numThreads = Integer.parseInt(input);
			
			// Create thread-pool executor and prime-finders.
			ExecutorService executor = Executors.newFixedThreadPool(numThreads);
			PrimeFinder[] primeFinders = new PrimeFinder[numThreads];
			for (int i = 0; i < numThreads; i++) {
				primeFinder = new PrimeFinder(MIN + i, MAX, numThreads);
				futures[i] = executor.submit(primeFinder);
			}
			Future<Integer>[] futures = new Future[numThreads];
			
			// Start timing.
			long start = System.nanoTime();
			
			// Submit tasks.
			for (int i = 0; i < numThreads; i++) {
				futures[i] = executor.submit(primeFinders[i]);
			}
			
			// Collect the results.
			int totalCounter = 0;
			for (int i = 0; i < numThreads; i++) {
				totalCounter += futures[i].get();
			}
			
			// Stop timing.
			long stop = System.nanoTime();

			// Shutdown thread-pool executor.
			executor.shutdown();

			// Output results.
			int numProcessors = Runtime.getRuntime().availableProcessors();
			System.out.println("Range searched: " + MIN + " - " + MAX);
			System.out.println("Number of primes found: " + totalCounter);
			System.out.println("Available processors: " + numProcessors);
			System.out.println("Number of threads: " + numThreads);
			System.out.println("Execution time (seconds): " + (stop-start)/1.0E9);
		}
		catch (Exception exception) {			
			System.out.println(exception);
		}
    }
}
