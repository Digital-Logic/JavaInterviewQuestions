package net.digitallogic;


import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Write a function that will return a String array containing the values from 1 to n except for,
 * 1. each number that is divisible by 3 store "fizz"
 * 2. each number that is divisible by 5 store "buzz"
 * 3. each number that is divisible by 3 and 5 store "fizzbuzz"
 * Examples:
 * 5 => {"1","2","fizz","4","buzz"}
 * 15 => {"1","2","fizz","4","buzz", "fizz","7","8","fizz","buzz","11","fizz","13","14","fizzbuzz"}
 */

public class FizzBuzz {
	public static String[] fizzbuzz(int n) {
		String[] result = new String[n];

		for (int i = 1; i <= n; ++i) {
			if (i % 3 == 0 && i % 5 == 0)
				result[i - 1] = "fizzbuzz";
			else if (i % 3 == 0)
				result[i - 1] = "fizz";
			else if (i % 5 == 0)
				result[i - 1] = "buzz";
			else result[i - 1] = String.valueOf(i);
		}

		return result;
	}

	public static String[] intStream(int n) {
		return IntStream.range(1, n + 1)
			// .parallel()
			.mapToObj(i -> {
				if (i % 3 == 0 && i % 5 == 0)
					return "fizzbuzz";
				if (i % 3 == 0)
					return "fizz";
				if (i % 5 == 0)
					return "buzz";

				return String.valueOf(i);
			})
			.toArray(String[]::new);
	}

	// Stream.generate generates an unordered stream, it is not
	// the recommended approach if you need an ordered stream, like
	// in this case. This solution will not provide consistent result
	// if ran in parallel.
	public static String[] streamGenerator(int n) {
		return Stream.generate(new Supplier<Integer>() {
			private int index = 1;

			@Override
			public Integer get() {
				return index++;
			}
		})
			.limit(n)
			.map(i -> {
				if (i % 3 == 0 && i % 5 == 0)
					return "fizzbuzz";
				if (i % 3 == 0)
					return "fizz";
				if (i % 5 == 0)
					return "buzz";

				return String.valueOf(i);
			})
			.toArray(String[]::new);
	}
}
