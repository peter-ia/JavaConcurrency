// Peter Idestam-Almquist, 2018-02-25.

package peteria.javaconcurrency.producerconsumer;

class Task {
	private String data;
	
	Task(String data) { 
		this.data = data; 
	}
	
	String getData() {
		return data;
	}
}
	
