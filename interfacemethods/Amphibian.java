// Peter Idestam-Almquist, 2017-02-26.
// Interface methods.

package peteria.javaconcurrency.interfacemethods;

class Amphibian implements Car, Boat {
	// Need to override conflicting inherited default methods.
	public String getId() {
		return ((Car) this).getId();
	}
	
	public static void main(String[] args) {
		int result = Car.compareIds("ABC123", "DEF456");
		System.out.println(result);
	}
}
