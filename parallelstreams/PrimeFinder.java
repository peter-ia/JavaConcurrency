// Peter Idestam-Almquist, 2017-03-01.
// Computationally intensive program.
// Using parallel stream.

package peteria.javaconcurrency.parallelstreams;

import java.util.stream.LongStream;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

class PrimeFinder {
	private final static long MIN = 1;
	private final static long MAX = 2000000;
	
	// Inefficient implementation of isPrime on purpose.
	static boolean isPrime(long number) {
		boolean result = true;
		for (long d = 2; d < Math.sqrt(number); d++) {
			if (number % d == 0)
				result = false; 
		}
		return result;
	}
	
	public static void main(String[] args) {
		try {
			// Start timing.
			long start = System.nanoTime();
			
			// Execute using parallel stream.
			long totalCounter = 
				LongStream
				.range(MIN, MAX)
				.parallel()
				.filter(PrimeFinder::isPrime)
				.count();
				
			// Stop timing.
			long stop = System.nanoTime();

			// Output results.
			int numProcessors = Runtime.getRuntime().availableProcessors();
			int parallelism = ForkJoinPool.commonPool().getParallelism();
			System.out.println("Range searched: " + MIN + " - " + MAX);
			System.out.println("Number of primes found: " + totalCounter);
			System.out.println("Available processors: " + numProcessors);
			System.out.println("Parallelism (threads): " + parallelism);
			System.out.println("Execution time (seconds): " + (stop-start)/1.0E9);
		}
		catch (Exception exception) {			
			System.out.println(exception);
		}
    }
}
