// Peter Idestam-Almquist, 2017-03-06.

package peteria.javaconcurrency.immutability;

class Program {
	static int[] array = new int[] { 30, 72, 7, 51, 2, 23, 63, 33, 31, 47 };
	static int sum = 0;
	
	// Sum integers using a mutable variable.
	static int sum1() {
		for (int i = 0; i < array.length; i++)
			sum = sum + array[i];
		return sum;
	}
	
	// Sum integers by passing values (functional way).
	static int sum2(int index, int sum) {
		if (index < 0)
			return sum;
		return sum2(index - 1, sum + array[index]);
	}
	
	public static void main(String[] args) {
		System.out.println(sum1());
		System.out.println(sum2(array.length - 1, 0));
	}
}
