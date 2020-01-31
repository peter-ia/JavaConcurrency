// Peter Idestam-Almquist, 2017-01-17.
// Computationally intensive program.

package peteria.javaconcurrency.benefits;

import java.io.BufferedReader;
import java.io.InputStreamReader;

class PrimeFinder implements Runnable {
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
		for (long d = 2; d < Math.sqrt(number); d++) {
			if (number % d == 0)
				result = false; 
		}
		return result;
	}
	
	public void run() {
		for (long number = min; number <= max; number = number + step) {
			if (isPrime(number))
				counter++;
		}
	}
	
	public static void main(String[] args) {
		try {
			// Read input.
			InputStreamReader streamReader = new InputStreamReader(System.in);
			BufferedReader consoleReader = new BufferedReader(streamReader);
			System.out.print("Input (numThreads)>");
			String input = consoleReader.readLine();
			int numThreads = Integer.parseInt(input);
			
			// Start timing.
			long start = System.nanoTime();
			
			// Create threads.
			Thread[] threads = new Thread[numThreads];
			PrimeFinder[] primeFinders = new PrimeFinder[numThreads];			
			for (int i = 0; i < numThreads; i++) {
				primeFinders[i] = new PrimeFinder(MIN + i, MAX, numThreads);
				threads[i] = new Thread(primeFinders[i]);
			}
			
			// Run threads.
			int totalCounter = 0;
			for (int i = 0; i < numThreads; i++) {
				threads[i].start();
			}
			for (int i = 0; i < numThreads; i++) {
				threads[i].join();
				totalCounter += primeFinders[i].counter;
			}

			// Stop timing.
			long stop = System.nanoTime();

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
