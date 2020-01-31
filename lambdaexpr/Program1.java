// Peter Idestam-Almquist, 2017-02-26.
// Lambda expressions.

package peteria.javaconcurrency.lambdaexpr;

import java.util.Arrays;
import java.util.Comparator;

class Program1 {
	public static void main(String[] args) {
		String[] words = new String[] { "anna", "bo", "cecilia", "dan", "erika" };

		// Abstract class.
		Comparator<String> comparator1 = new Comparator<String>() {
			public int compare(String str1, String str2) {
			if (str1.length() < str2.length())
				return -1;
			else if (str1.length() > str2.length())
				return 1;
			else
				return 0;
			}
		};
		
		// Lambda expression.
		Comparator<String> comparator2 = (String str1, String str2) -> {
			if (str1.length() < str2.length())
				return -1;
			else if (str1.length() > str2.length())
				return 1;
			else
				return 0;
		};
		
		// Lambda expression with type inference.
		Comparator<String> comparator3 = (str1, str2) -> {
			if (str1.length() < str2.length())
				return -1;
			else if (str1.length() > str2.length())
				return 1;
			else
				return 0;
		};
		
		// Second parameter should be an instance of Comparator.
		Arrays.sort(words, comparator3); 

		for (int i = 0; i < words.length; i++) {
			System.out.println(words[i] + " ");
		}
		
		// Shorter alternative.
		Arrays.sort(words, (str1, str2) -> 
			Integer.compare(str1.length(), str2.length())
		);

		for (int i = 0; i < words.length; i++) {
			System.out.println(words[i] + " ");
		}
	}
}
