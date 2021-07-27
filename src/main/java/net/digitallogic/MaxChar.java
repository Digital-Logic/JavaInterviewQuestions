package net.digitallogic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
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

	public static String getMaxChar(String str) {
		Map<String, Integer> charMap = new HashMap<>();

		Pattern pattern = Pattern.compile("\\w");
		Matcher matcher = pattern.matcher(str.toLowerCase());

		while(matcher.find()) {
			String c = matcher.group();
			charMap.put(c, charMap.getOrDefault(c, 0) + 1);
		}

		Iterator<String> it = charMap.keySet().iterator();

		String max = it.next();

		while(it.hasNext()) {
			String next = it.next();
			if (charMap.get(next) > charMap.get(max))
				max = next;
		}

		return max;
	}

	public static String maxCharStream(String str) {
		Pattern pattern = Pattern.compile("\\w");

		return Arrays.stream(str.toLowerCase().split(""))
			.filter(pattern.asPredicate())
			.collect(Collectors.toMap(c -> c, c -> 1, Integer::sum))
			.entrySet()
			.stream()
			.max(Map.Entry.comparingByValue())
			.orElseThrow()
			.getKey();
	}

	public static char intArray(String str) {
		int[] charMap = new int[26];

		Matcher matcher = Pattern.compile("\\w").matcher(str.toLowerCase());

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
}
