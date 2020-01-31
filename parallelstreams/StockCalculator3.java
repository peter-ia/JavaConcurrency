// Peter Idestam-Almquist, 2017-03-02.
// IO-intensive program.
// Parallel stream and race condition.

package peteria.javaconcurrency.parallelstreams;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;

class StockCalculator3 {
	static final String FILENAME = "stocks.csv";
	static final String MAIN_URL = "http://ichart.finance.yahoo.com/table.csv?s=";
	static private double totalValue = 0;
	
	private static List<Stock> readStocksFromFile() 
	throws FileNotFoundException, IOException {
		List<Stock> all = new ArrayList<Stock>();
		BufferedReader fileReader = new BufferedReader(new FileReader(FILENAME));
		String line = fileReader.readLine(); 
		while (line != null) {
			String[] items = line.split(",");
			Stock stock = new Stock(items[0], Integer.parseInt(items[1]));
			all.add(stock);
			line = fileReader.readLine();
		}
		return all;
	}
	
	private static void updateValue(Stock stock) {
		try {
			double tempValue = totalValue;
			URL url = new URL(MAIN_URL + stock.ticker);
			InputStream inStream = url.openStream();
			InputStreamReader streamReader = new InputStreamReader(inStream);
			BufferedReader reader = new BufferedReader(streamReader);
			String line = reader.readLine(); // Discard header.
			line = reader.readLine(); // Latest info is in first line.
			String[] items = line.split(","); // Split into columns.
			double price = Double.parseDouble(items[items.length - 1]); // Price is in last column.
			totalValue = tempValue + price * stock.quantity;
		}
		catch (Exception exception) {
			System.out.println(exception);
		}
	}
	
	public static void main(String[] args) {
		try {
			// Read data from file.
			System.out.println("Reading stocks from file: " + FILENAME);
			List<Stock> all = readStocksFromFile();
			
			// Start timing.
			long start = System.nanoTime();
			
			// Execute using parallel stream and explicit ForkJoinPool.
			all.parallelStream().forEach(StockCalculator3::updateValue);
			
			// Stop timing.
			long stop = System.nanoTime();

			// Output results.
			int numProcessors = Runtime.getRuntime().availableProcessors();
			int parallelism = ForkJoinPool.commonPool().getParallelism();
			System.out.println("Number of stocks: " + all.size());
			System.out.println("Total stock value (USD): " + totalValue);
			System.out.println("Available processors: " + numProcessors);
			System.out.println("Parallelism (threads): " + parallelism);
			System.out.println("Execution time (seconds): " + (stop-start)/1.0E9);
		}
		catch (Exception exception) {			
			System.out.println(exception);
		}
    }
}
