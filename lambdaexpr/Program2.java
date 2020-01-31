// Peter Idestam-Almquist, 2017-02-26.
// Variable scope (effectively final).

package peteria.javaconcurrency.lambdaexpr;

class Program2 {

	// Abstract class.
	static void repeatString1(String str, int count) {
		new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < count; i++) {
					System.out.println(str + " ");
				}
			}
		}).start();
	}
	
	// Lambda expression.
	static void repeatString2(String str, int count) {
		new Thread(() -> {
			for (int i = 0; i < count; i++) {
				System.out.println(str + " ");
			}
		}).start();
	}
	
	public static void main(String[] args) {
		repeatString1("anna", 3);
		repeatString1("bo", 4);
	}
}
