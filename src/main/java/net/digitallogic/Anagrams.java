package net.digitallogic;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Given two strings, write a function that will determine if one string is
 * an anagram of another. An anagram is a string that is formed by rearranging
 * the letters of another string.
 * Ignore capitalization, and spaces. Punctuation will not be included in the provided strings.
 *
 * Examples
 * "Arc" is an anagram of "Car"
 * "Debit card" is an anagram of "Bad credit"
 * "Astronomer" is an anagram of "Moon starer"
 */
public class Anagrams {

	public static boolean sorted(String strA, String strB) {
		char[] charA = strA.toLowerCase()
			.replace(" ", "")
			.toCharArray();

		char[] charB = strB.toLowerCase()
			.replace(" ", "")
			.toCharArray();

		Arrays.sort(charA);
		Arrays.sort(charB);

		if (charA.length != charB.length)
			return false;

		for (int i=0; i<charA.length; ++i)
			if (charA[i] != charB[i])
				return false;

		return true;
	}

	public static boolean hashMap(String strA, String strB) {
		Map<String, Integer> mapA = strToMap(strA);
		Map<String, Integer> mapB = strToMap(strB);

		if (mapA.size() != mapB.size())
			return false;

		for (Entry<String, Integer> entry : mapA.entrySet()) {
			if (!entry.getValue().equals(mapB.get(entry.getKey())))
				return false;
		}
		return true;
	}

	private static Map<String, Integer> strToMap(String str) {
		Pattern p = Pattern.compile("\\w");

		return Arrays.stream(str.toLowerCase().split(""))
			.filter(p.asPredicate())
			.collect(Collectors.toMap(s -> s, c -> 1, Integer::sum));
	}

	public static boolean intArray(String strA, String strB) {
		int[] charMap = new int[26];

		String copyA = strA.toLowerCase().replace(" ", "");
		for (char c:copyA.toCharArray())
			charMap[c - 'a']++;

		String copyB = strB.toLowerCase().replace(" ", "");
		for (char c:copyB.toCharArray())
			charMap[c - 'a']--;

		for (int i:charMap)
			if (i != 0) return false;

		return true;
	}
}
