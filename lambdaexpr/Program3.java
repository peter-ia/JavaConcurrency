// Peter Idestam-Almquist, 2017-02-26.
// Functional interfaces.

package peteria.javaconcurrency.lambdaexpr;

class Program3 {
	static void trippleRepeat(Repeater repeater) {
		repeater.repeat(3);
	}
	
	public static void main(String[] args) {
		trippleRepeat((count) -> {
			for (int i = 0; i < count; i++)
				System.out.println("anna");
		});
	}
}
