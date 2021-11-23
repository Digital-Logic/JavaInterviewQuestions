package net.digitallogic;

/**
 * Write a function that will take an integer and reverse the order of the numbers
 * within that integer.
 * Examples
 * 123 => 321
 * 450 => 54
 * -763 => -367
 * -20 => -2
 */
public class ReverseInt {

	public static int reverseString(int n) {
		return Integer.signum(n) *
			Integer.parseInt(
				new StringBuilder(Integer.toString(Math.abs(n)))
					.reverse().toString()
			);
	}

	public static int reverseInt(int n) {
		int sign = Integer.signum(n);
		n = Math.abs(n);

		int result = 0;

		while (n > 0) {
			int next = n % 10;
			result = result * 10 + next;
			n /= 10;
		}

		return result * sign;
	}
}
