package net.digitallogic;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Given two strings, write a function that will determine if one string is
 * an anagram of another. An anagram is a string that is formed by rearranging
 * the letters of another string.
 * Ignore capitalization, punctuation, and spaces.
 *
 * Examples
 *  isAnagram("Arc", "Car") => true
 *  isAnagram("D e b i t card", "Bad credit") => true
 *  isAnagram("Astronomer!", "Moon starer") => true
 */
public class Anagrams {

	public static boolean sorted(String strA, String strB) {
		Pattern pattern = Pattern.compile("[^a-z0-9]");

		char[] charA = pattern.matcher(strA.toLowerCase())
			.replaceAll("")
			.toCharArray();

		char[] charB = pattern.matcher(strB.toLowerCase())
			.replaceAll("")
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
		return Pattern.compile("[a-z0-9]")
			.matcher(str.toLowerCase())
			.results()
			.map(MatchResult::group)
			.collect(Collectors.toMap(s -> s, c -> 1, Integer::sum));
	}

	public static boolean intArray(String strA, String strB) {
		Pattern pattern = Pattern.compile("[\\W_]");

		int[] charMap = new int[26];

		String copyA = pattern.matcher(strA.toLowerCase()).replaceAll("");
		for (char c:copyA.toCharArray())
			charMap[c - 'a']++;

		String copyB = pattern.matcher(strB.toLowerCase()).replaceAll("");
		for (char c:copyB.toCharArray())
			charMap[c - 'a']--;

		for (int i:charMap)
			if (i != 0) return false;

		return true;
	}
}
