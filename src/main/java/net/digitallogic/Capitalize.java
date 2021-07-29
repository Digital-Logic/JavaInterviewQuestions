package net.digitallogic;

import java.util.Arrays;
import java.util.Locale;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Capitalize the first letter in each word in the provided string.
 * Examples
 * Input: "can i help you?", Output: "Can I Help You?"
 */
public class Capitalize {

	public static String capitalize(String str) {
		String[] words = str.split(" ");

		for (int i=0; i<words.length; ++i)
			words[i] = words[i].substring(0,1).toUpperCase() + words[i].substring(1);

		return String.join(" ", words);
	}

	public static String stringJoiner(String str) {
		String[] words = str.split(" ");

		StringJoiner joiner = new StringJoiner(" ");

		for (String word:words)
			joiner.add(word.substring(0,1).toUpperCase() + word.substring(1));

		return joiner.toString();
	}

	public static String streams(String str) {
		return Arrays.stream(str.split(" "))
			.map(word -> word.substring(0,1).toUpperCase() + word.substring(1))
			.collect(Collectors.joining(" "));
	}
}
