package net.digitallogic;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * Write a function that will return the provided string
 * with the letter in reverse order.
 * Examples
 * Input: "car", Output: "rac"
 * Input: "dog", Output: "god"
 * <p>
 * Try to create more than one solution.
 */
public class ReverseString {

	// Slow solution, string concatenation within a loop
	// should be avoided.
	public static String appendString(String str) {
		String result = "";

		for (char c : str.toCharArray())
			result = c + result;

		return result;
	}

	public static String charArray(String str) {
		char[] result = str.toCharArray();

		// inplace reverse
		for (int f = 0, b = result.length - 1; f < b; ++f, --b) {
			// swap characters
			char temp = result[f];
			result[f] = result[b];
			result[b] = temp;
		}

		return String.valueOf(result);
	}

	public static String stringArray(String str) {
		String[] result = new String[str.length()];
		for (int f = 0, b = str.length() - 1; f < result.length; ++f, --b)
			result[f] = String.valueOf(str.charAt(b));

		return String.join("", result);
	}

	public static String stringList(String str) {
		List<String> result = Arrays.asList(str.split(""));
		Collections.reverse(result);
		return String.join("", result);
	}

	public static String stringJoiner(String str) {
		StringJoiner joiner = new StringJoiner("");

		for (int i = str.length() - 1; i >= 0; --i)
			joiner.add(String.valueOf(str.charAt(i)));

		return joiner.toString();
	}

	public static String stringBuilder(String str) {
		return new StringBuilder(str)
			.reverse()
			.toString();
	}

	public static String stack(String str) {
		Deque<String> result = new ArrayDeque<>();

		for (char c : str.toCharArray())
			result.push(String.valueOf(c));

		return String.join("", result);
	}

	// Performs string concatenation within a loop, in this case
	// the loop is the reduce function.
	public static String streamAppend(String str) {
		return str.chars()
			.mapToObj(i -> String.valueOf((char) i))
			.reduce("", (result, c) -> c + result);
	}

	public static String streamLinked(String str) {
		return String.join("", Arrays.stream(str.split(""))
			.collect(LinkedList<String>::new,
				LinkedList<String>::offerFirst,
				(l1, l2) -> l1.addAll(0, l2)
			));
	}

	public static String streamStack(String str) {
		return Arrays.stream(str.split(""))
			.collect(
				Collector.of(
					ArrayDeque<String>::new,
					ArrayDeque<String>::offerFirst,
					(a1, a2) -> {
						a2.addAll(a1);
						return a2;
					},
					arr -> String.join("", arr)
				)
			);
	}

	public static String streamBuilder(String str) {
		return str.chars()
			.collect(StringBuilder::new,
				(sb, i) -> sb.insert(0, (char) i),
				(sb1, sb2) -> sb1.insert(0, sb2))
			.toString();
	}
}
