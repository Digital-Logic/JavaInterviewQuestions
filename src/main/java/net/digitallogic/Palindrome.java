package net.digitallogic;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

/**
 * Given a String, determine if the string is a palindrome.
 * A palindrome is a sequence of characters that reads the same backwards or forwards.
 * Ignore capitalization, punctuation, and spaces.
 * Example
 * "hannah!" => true
 * "Taco cat" => true
 * "dog" => false
 */
public class Palindrome {

	public static boolean replace(String str) {
		String copy = str.toLowerCase().replaceAll("\\W", "");

		for (int f=0, b=copy.length()-1; f<b; ++f, --b) {
			if (copy.charAt(f) != copy.charAt(b))
				return false;
		}
		return true;
	}

	public static boolean select(String str) {
		String[] copy = Pattern.compile("\\w")
			.matcher(str.toLowerCase())
			.results()
			.map(MatchResult::group)
			.toArray(String[]::new);

		for (int f=0, b=copy.length-1; f<b; ++f, --b) {
			if (!copy[f].equals(copy[b]))
				return false;
		}
		return true;
	}
}
