// Peter Idestam-Almquist, 2017-02-26.
// Interface methods.

package peteria.javaconcurrency.interfacemethods;

interface Car {
	default String getId() {
		return "UNKNOWN";
	}
		
	static int compareIds(String id1, String id2) {
		return id1.compareTo(id2);
	}
}
