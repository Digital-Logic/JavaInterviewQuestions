package net.digitallogic;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * YouTube: https://youtu.be/WAJaPZEJylA
 *
 * Write a function that will produce the n-th number within the fibonacci sequence.
 * The fibonacci sequence is:
 * 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233
 * The next number in the fibonacci sequence is the sum of the previous two numbers.
 * fib(n) = fib(n-1) + fib(n-2)
 *
 * Examples
 * fib(1) = 1
 * fib(2) = 1
 * fib(3) = 2
 * fib(4) = 3
 * fib(5) = 5
 * fib(8) = 21
 */

public class Fibonacci {
	public static long iterative(int n) {
		long[] cache = new long[n+1];

		cache[0]=0;
		cache[1]=1;

		for(int i=2; i<=n; ++i)
			cache[i] = cache[i-1] + cache[i-2];

		return cache[n];
	}

	public static long iterativeTwo(int n) {
		long[] cache = {0,1};

		for (int i=2; i<=n; ++i) {
			cache[i%2] = cache[0] + cache[1];
		}

		return cache[n%2];
	}

	public static long recursive(int n) {
		if (n<=1)
			return n;
		return recursive(n-1) + recursive(n-2);
	}

	public static long recursiveMemoized(int n) {
		long[] memo = new long[n+1];
		return recursiveMemoized(n, memo);
	}
	private static long recursiveMemoized(int n, long[] memo) {
		if (n<=1)
			return n;

		if (memo[n] == 0)
			memo[n] = recursiveMemoized(n-1, memo) + recursiveMemoized(n-2, memo);

		return memo[n];
	}


	public static Function<Integer,Long> memoizedFib = memoizer(n -> {
		if (n <= 1)
			return (long) n;
		return Fibonacci.memoizedFib.apply(n-1) + Fibonacci.memoizedFib.apply(n-2);
	});

	private static <T,R> Function<T,R> memoizer(Function<T,R> func) {
		final Map<T,R> memo = new HashMap<>();

		return (T t) -> {
			if (!memo.containsKey(t))
				memo.put(t, func.apply(t));

			return memo.get(t);
		};
	}
}
