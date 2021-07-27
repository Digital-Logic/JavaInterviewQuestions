package net.digitallogic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Write a function that can determine if a String contains only unique characters.
 * That is a character can only appear once within the string.
 * Ignore capitalization.
 *
 * Examples
 * Input: "hello", Output: false
 * Input: "kind", Output: true
 * Input: "Denver", Output: false
 * Input: "Alpha", Output: false
 *
 * Extra Credit!!!
 * Can you perform the check without any additional data structures?
 */
public class IsUnique {

	public static boolean sorted(String str) {
		String[] strAry = str.toLowerCase().split("");
		Arrays.sort(strAry);

		for (int i=0; i<strAry.length-1; ++i) {
			if (strAry[i].equals(strAry[i+1]))
				return false;
		}
		return true;
	}

	public static boolean hashMap(String str) {
		Map<String, Integer> charMap = Arrays.stream(str.toLowerCase().split(""))
			.collect(Collectors.toMap(c -> c, c -> 1, Integer::sum));

		return charMap.values().stream()
			.filter(i -> i > 1)
			.findAny()
			.isEmpty();
	}

	public static boolean extraCredit(String str) {
		int bitMask = 0;

		for (int i=0; i<str.length(); ++i) {
			int val = str.charAt(i) - 'a';

			if ((bitMask & (1 << val)) != 0)
				return false;

			bitMask |= (1<<val);
		}
		return true;
	}
}
