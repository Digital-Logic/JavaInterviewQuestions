package net.digitallogic;

import java.util.Arrays;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Write a function that returns the number of vowels in a string.
 * Vowels are the characters a,e,i,o,u.
 * Examples
 * "Hello world" => 3
 * "Apples are red" => 5
 */
public class Vowels {

	public static int countVowels(String str) {
		Set<String> vowels = Set.of("a","e","i","o","u");

		int count=0;

		for (char c:str.toCharArray())
			if (vowels.contains(String.valueOf(c).toLowerCase()))
				count++;

		return count;
	}

	public static int matchVowels(String str) {
		Matcher matcher = Pattern.compile("[aeiou]", Pattern.CASE_INSENSITIVE).matcher(str);

		return (int) matcher.results().count();
	}

	public static int streamVowels(String str) {
		Pattern pattern = Pattern.compile("[aeiou]", Pattern.CASE_INSENSITIVE);

		return (int) Arrays.stream(str.split(""))
			.filter(pattern.asPredicate())
			.count();
	}

	// Use a set as the predicate
	public static int streamVowelsSet(String str) {
		Set<String> vowels = Set.of("a","e","i","o","u");

		return (int) Arrays.stream(str.split(""))
			.map(String::toLowerCase)
			.filter(vowels::contains)
			.count();
	}
}