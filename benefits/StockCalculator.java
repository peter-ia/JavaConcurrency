// Peter Idestam-Almquist, 2017-03-01.
// IO-intensive program.

package peteria.javaconcurrency.benefits;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

class StockCalculator implements Runnable {
	static final String FILENAME = "stocks.csv";
	static final String MAIN_URL = "http://ichart.finance.yahoo.com/table.csv?s=";

	List<Stock> partition;
	double value;
	
	// Constructor.
	StockCalculator(List<Stock> partition) {
		this.partition = partition;
		value = 0.0;
	}
	
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
	
	private static List<List<Stock>> partitionStocks(List<Stock> all, int num) {
		// Create data structure.
		List<List<Stock>> partitions = new ArrayList<List<Stock>>();
		for (int i = 0; i < num; i++) {
			partitions.add(new ArrayList<Stock>());
		}
		
		// Fill data structure.
		for (int i = 0; i < all.size(); i++) {
			partitions.get(i % num).add(all.get(i));
		}
		
		return partitions;
	}
	
	private static double getValue(Stock stock) {
		try {
			URL url = new URL(MAIN_URL + stock.ticker);
			InputStream inStream = url.openStream();
			InputStreamReader streamReader = new InputStreamReader(inStream);
			BufferedReader reader = new BufferedReader(streamReader);
			String line = reader.readLine(); // Discard header.
			line = reader.readLine(); // Latest info is in first line.
			String[] items = line.split(","); // Split into columns.
			double price = Double.parseDouble(items[items.length - 1]); // Price is in last column.
			return price * stock.quantity;
		}
		catch (Exception exception) {
			System.out.println(exception);
			return 0;
		}
	}
	
	public void run() {
		try {
			for (int i = 0; i < partition.size(); i++) {
				value += getValue(partition.get(i));
			}
		}
		catch (Exception exception) {
			System.out.println(exception);
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
			
			// Read data from file.
			System.out.println("Reading stocks from file: " + FILENAME);
			List<Stock> all = readStocksFromFile();
			
			// Start timing.
			long start = System.nanoTime();
			
			// Partition the data.
			List<List<Stock>> partitions = partitionStocks(all, numThreads);

			// Create threads.
			Thread[] threads = new Thread[numThreads];
			StockCalculator[] calculators = new StockCalculator[numThreads];			
			for (int i = 0; i < numThreads; i++) {
				calculators[i] = new StockCalculator(partitions.get(i));
				threads[i] = new Thread(calculators[i]);
			}
			
			// Run threads.
			double totalValue = 0.0;
			for (int i = 0; i < numThreads; i++) {
				threads[i].start();
			}
			for (int i = 0; i < numThreads; i++) {
				threads[i].join();
				totalValue += calculators[i].value;
			}
			
			// Stop timing.
			long stop = System.nanoTime();

			// Output results.
			System.out.println("Number of stocks: " + all.size());
			System.out.println("Total stock value (USD): " + totalValue);
			System.out.println("Number of threads: " + numThreads);
			System.out.println("Execution time (seconds): " + (stop-start)/1.0E9);
		}
		catch (Exception exception) {			
			System.out.println(exception);
		}
    }
}
