package net.digitallogic;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * YouTube: https://youtu.be/7_EZJcKhoN4
 *
 * Write a function that will return the character in the provided string that
 * repeats the most.
 * Make sure to ignore capitalization, punctuation, and spaces.
 *
 * Examples
 * Input: "Hello world", Output: "l"
 * Input: "Hello......world", Output: "l"
 *
 * The return value of your function can be a String or a char.
 */
public class MaxChar {

	public static char intArray(String str) {
		int[] charMap = new int[26];

		Matcher matcher = Pattern.compile("[a-z]").matcher(str.toLowerCase());

		while (matcher.find()) {
			char c = matcher.group().charAt(0);
			charMap[c - 'a']++;
		}

		int max=0;
		for (int i=1; i<charMap.length; ++i) {
			if (charMap[i] > charMap[max])
				max = i;
		}

		return (char)(max + 'a');
	}

	public static String maxCharMap(String str) {
		Map<String, Integer> charMap = new HashMap<>();

		Pattern.compile("(?!_)\\w")
			.matcher(str.toLowerCase())
			.results()
			.forEach(r -> {
				charMap.put(r.group(), charMap.getOrDefault(r.group(), 0) + 1);
			});

		return charMap.entrySet()
			.stream()
			//.max((a, b) -> a.getValue() - b.getValue())
			.max(Map.Entry.comparingByValue())
			.orElseThrow()
			.getKey();
	}

	public static String maxCharStream(String str) {
		return Pattern.compile("[a-z]")
			.matcher(str.toLowerCase())
			.results()
			.collect(Collectors.toMap(
				MatchResult::group,
				r -> 1,
				Integer::sum
			))
			.entrySet()
			.stream()
			.max(Map.Entry.comparingByValue())
			.orElseThrow()
			.getKey();
	}
}
