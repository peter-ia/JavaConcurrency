// Peter Idestam-Almquist, 2017-01-16.

package peteria.javaconcurrency.benefits;

class Stock {
	String ticker;
	int quantity;
	
	Stock(String ticker, int quantity) {
		this.ticker = ticker;
		this.quantity = quantity;
	}
}
