// Peter Idestam-Almquist, 2017-03-01.

package peteria.javaconcurrency.parallelstreams;

class Stock {
	String ticker;
	int quantity;
	
	Stock(String ticker, int quantity) {
		this.ticker = ticker;
		this.quantity = quantity;
	}
}
