// Peter Idestam-Almquist, 2017-02-26.
// Streams.

package peteria.javaconcurrency.streams;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.Collectors;

class Program {
	public static void main(String[] args) {
		String[] wordArray = new String[] { "erika", "dan", "anna", "cecilia", "bo" };
		List<String> wordList = new ArrayList<String>();
		wordList.add("anna");
		wordList.add("bo");
		wordList.add("cecilia");
		wordList.add("dan");
		wordList.add("erika");
		
		// Stream creation.
		Stream<String> stream1 = Arrays.stream(wordArray);
		Stream<String> stream2 = wordList.stream();
		Stream<String> stream3 = Stream.of(wordArray);
		Stream<String> stream4 = Stream.of("anna", "bo", "cecilia", "dan", "erika");
		Stream<Double> stream5 = Stream.generate(Math::random);
		Stream<Integer> stream6 = Stream.iterate(0, (x) -> x + 1);
		
		//Intermediate operations.
		Stream<String> stream7 = stream1.filter((x) -> x.length() < 4);
		Stream<String> stream8 = stream2.map((x) -> x.toUpperCase());
		Stream<String> stream9 = stream3.map(String::toUpperCase);
		Stream<String> stream10 = stream4.sorted(String::compareTo);
		Stream<Double> stream11 = stream5.limit(5);
		Stream<Integer> stream12 = stream6.skip(2).limit(3);
		
		//Terminal operations.
		Optional<String> max = 
			stream7.max((x, y) -> Integer.compare(x.length(), y.length()));
		if (max.isPresent())
			System.out.println(max.get());
		long count = stream11.count();
		System.out.println(count);
		stream9.forEach(System.out::println);
		Object[] objArray = stream10.toArray();
		for (int i = 0; i < objArray.length; i++)
			System.out.println(objArray[i]);
		List<Integer> intList = stream12.collect(Collectors.toList());
		for (int i = 0; i < intList.size(); i++)
			System.out.println(intList.get(i));
		Map<Integer, String> wordByLength = 
			stream8.collect(Collectors.toMap(String::length, Function.identity()));
		String str = wordByLength.get(3);
		System.out.println(str);
	}
}
