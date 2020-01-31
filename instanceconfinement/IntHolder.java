// Peter Idestam-Almquist, 2017-02-22.
// Int wrapper class.

package peteria.javaconcurrency.instanceconfinement;

class IntHolder {
	private int value;
	
	IntHolder(int value) {
		this.value = value;
	}
	
	int get() {
		return value;
	}
	
	void set(int value) {
		this.value = value;
	}
}
