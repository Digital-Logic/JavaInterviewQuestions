package net.digitallogic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
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
		Set<Character> vowels = new HashSet<>(Set.of('a','e', 'i','o','u'));

		int count=0;

		for (char c:str.toLowerCase().toCharArray())
			if (vowels.contains(c))
				count++;

		return count;
	}

	public static int matchVowels(String str) {
		return (int) Pattern.compile("[aeiou]", Pattern.CASE_INSENSITIVE)
			.matcher(str)
			.results()
			.count();
	}

	public static int splitString(String str) {
		Pattern pattern = Pattern.compile("[aeiou]", Pattern.CASE_INSENSITIVE);

		return (int) Arrays.stream(str.split(""))
			.filter(pattern.asPredicate())
			.count();
	}

	public static int splitSet(String str) {
		Set<Character> vowels = new HashSet<>(Set.of('a','e', 'i','o','u'));

		return (int) str.chars()
			.mapToObj(c -> (char) c)
			.map(Character::toLowerCase)
			.filter(vowels::contains)
			.count();
	}
}
