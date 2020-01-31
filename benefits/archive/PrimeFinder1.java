// Peter Idestam-Almquist, 2017-01-13.
// Single-threaded execution.

package peteria.javaconcurrency.benefits;

import java.io.BufferedReader;
import java.io.InputStreamReader;

class PrimeFinder1 {
	private long min;
	private long max;
	private int step;
	private int counter;
	
	PrimeFinder1(long min, long max, int step) {
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
			System.out.print("Input (max)>");
			String input = consoleReader.readLine();
			long min = 0;
			long max = Long.parseLong(input);

			// Start timing.
			long start = System.nanoTime();

			// Run.
			int totalCounter = 0;
			PrimeFinder1 primeFinder = new PrimeFinder1(min, max, 1);
			primeFinder.run();
			totalCounter += primeFinder.counter;
			
			// Stop timing.
			long stop = System.nanoTime();

			// Output results.
			System.out.println("Range searched: " + min + " - " + max);
			System.out.println("Number of primes found: " + totalCounter);
			System.out.println("Execution time (seconds): " + (stop-start)/1.0E9);
		}
		catch (Exception exception) {			
			System.out.println(exception);
		}
    }
}
