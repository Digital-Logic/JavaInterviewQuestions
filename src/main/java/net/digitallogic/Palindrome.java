package net.digitallogic;

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

	public static boolean isPalindrome(String str) {
		String copy = str.toLowerCase().replaceAll("\\W", "");

		for (int f=0, b=copy.length()-1; f<b; ++f, --b) {
			if (copy.charAt(f) != copy.charAt(b))
				return false;
		}
		return true;
	}
}
