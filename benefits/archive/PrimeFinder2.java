// Peter Idestam-Almquist, 2017-01-13.
// Multi-threaded execution.

package peteria.javaconcurrency.benefits;

import java.io.BufferedReader;
import java.io.InputStreamReader;

class PrimeFinder2 implements Runnable {
	private long min;
	private long max;
	private int step;
	private int counter;
	
	PrimeFinder2(long min, long max, int step) {
		this.min = min;
		this.max = max;
		this.step = step;
		counter = 0;
	}
	
	// Inefficient implementation of isPrime on purpose.
	static boolean isPrime(long number) {
		boolean result = true;
		for (long denominator = 2; denominator < Math.sqrt(number); denominator++) {
			if (number % denominator == 0)
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
			BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Input (max & numThreads)>");
			String[] inputs = consoleReader.readLine().split(" ");
			long min = 0;
			long max = Long.parseLong(inputs[0]);
			int numThreads = Integer.parseInt(inputs[1]);
			
			// Start timing.
			long start = System.nanoTime();
			
			// Create threads.
			Thread[] threads = new Thread[numThreads];
			PrimeFinder2[] primeFinders = new PrimeFinder2[numThreads];			
			for (int i = 0; i < numThreads; i++) {
				primeFinders[i] = new PrimeFinder2(min + i, max, numThreads);
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
			System.out.println("Range searched: " + min + " - " + max);
			System.out.println("Number of primes found: " + totalCounter);
			System.out.println("Available processors: " + Runtime.getRuntime().availableProcessors());
			System.out.println("Number of threads: " + numThreads);
			System.out.println("Execution time (seconds): " + (stop-start)/1.0E9);
		}
		catch (Exception exception) {			
			System.out.println(exception);
		}
    }
}
