// Peter Idestam-Almquist, 2017-01-16.
// Non-responsive console.

package peteria.javaconcurrency.benefits;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

class CommandLine1 {
	
	// Do some extensive work.
	public void run() {
		try {
			Thread.sleep(10000);
		}
		catch (Exception exception) {
			System.out.println(exception);
		}
	}
	
	public static void main(String[] args) {
		try {
			// Read first input.
			BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Input (run or quit)>");
			String input = consoleReader.readLine();
			
			// Repeat.
			while (!input.startsWith("quit")) {
				// Run in main thread.
				CommandLine1 instance = new CommandLine1();
				instance.run();
				
				// Read new input.
				System.out.print("Input (run or quit)>");
				input = consoleReader.readLine();
			}
		}
		catch (Exception exception) {			
			System.out.println(exception);
		}
    }
}
