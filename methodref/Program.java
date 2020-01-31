// Peter Idestam-Almquist, 2017-02-26.
// Method references.

package peteria.javaconcurrency.methodref;

import java.util.function.Function;
import java.util.function.BiFunction;
import java.util.function.Supplier;

class Program {
	static double execDoubleBiFunction(
		BiFunction<Double, Double, Double> function, 
		double param1, 
		double param2
	) {
		return function.apply(param1, param2);
	}
	
	static String execStringSupplier(Supplier<String> supplier) {
		return supplier.get();
	}
	
	static int execIntBiFunction(
		BiFunction<String, String, Integer> function, 
		String param1, 
		String param2
	) {
		return function.apply(param1, param2);
	}
	
	static int execIntFunction(
			Function<String, Integer> function, 
			String param
	) {
		return function.apply(param);
	}
	
	public static void main(String[] args) {
		// Lambda expression.
		double result1 = execDoubleBiFunction((x, y) -> Math.pow(x, y), 2, 3);
		System.out.println(result1);
		
		// Static method reference.
		double result2 = execDoubleBiFunction(Math::pow, 2, 3);
		System.out.println(result2);
		
		// Lambda expression.
		String result3 = execStringSupplier(() -> { return new String(); });
		System.out.println(result3);
		
		// Constructor reference.
		String result4 = execStringSupplier(String::new);
		System.out.println(result4);
		
		// Lambda expression.
		int result5 = execIntBiFunction((x, y) -> x.compareToIgnoreCase(y), "Anna", "ANNA");
		System.out.println(result5);
		
		// Instance method reference by type.
		int result6 = execIntBiFunction(String::compareToIgnoreCase, "Anna", "ANNA");
		System.out.println(result6);
		
		// Instance method reference by object.
		int result7 = execIntFunction("Anna"::compareToIgnoreCase, "ANNA");
		System.out.println(result7);
	}
}
