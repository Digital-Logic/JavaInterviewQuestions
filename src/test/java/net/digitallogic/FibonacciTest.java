package net.digitallogic;

import lombok.Value;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.lang.reflect.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class FibonacciTest {
	@Test
	void AtLestOneSolutionProvided() {
		assertThat(
			findTestMethods()
		).describedAs("No solutions found to test.")
			.hasAtLeastOneElementOfType(Method.class);
	}

	@TestFactory
	@SuppressWarnings("unchecked cast")
	Stream<DynamicNode> dynamicTests() {
		return Stream.concat(
			findTestFields()
				.map(field -> dynamicContainer(field.getName(), testCases()
					.map(args -> dynamicTest("Fib of " + args.getN(), () -> {
						try {
							Function<Integer, Long> func = (Function<Integer, Long>) field.get(new Fibonacci());
							assertThat(func.apply(args.getN())).isEqualTo(args.getExpected());
						} catch (IllegalAccessException | ClassCastException ex) {
							fail("Unable to test field: " + field.getName());
						}
					}))
				)),
			findTestMethods()
				.map(m -> dynamicContainer(m.getName(), testCases()
					.map(args -> dynamicTest("Fib of " + args.getN(), () -> {
						long result = (long) m.invoke(new Fibonacci(), args.getN());
						assertThat(result).isEqualTo(args.getExpected());
					}))
				))
		);
	}


	Stream<Field> findTestFields() {
		return stream(Fibonacci.class.getDeclaredFields())
			.filter(field -> field.getAnnotation(Disabled.class) == null)
			.filter(field -> {
				Type[] types = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
				return field.getType() == Function.class &&
					types[0] == Integer.class &&
					types[1] == Long.class;
			});

	}

	Stream<Method> findTestMethods() {
		return stream(Fibonacci.class.getDeclaredMethods())
			.filter(m -> Modifier.isPublic(m.getModifiers()))
			.filter(m -> m.getAnnotation(Disabled.class) == null)
			.filter(m -> {
				Class<?>[] args = m.getParameterTypes();
				return args.length == 1 &&
					args[0] == Integer.TYPE &&
					m.getReturnType() == Long.TYPE;
			});
	}

	@Value(staticConstructor = "of")
	private static class Args {
		int n;
		long expected;
	}

	Stream<Args> testCases() {
		return Stream.of(
			Args.of(1, 1),
			Args.of(2, 1),
			Args.of(3, 2),
			Args.of(4, 3),
			Args.of(5, 5),
			Args.of(6, 8),
			Args.of(20, 6765),
			Args.of(25, 75025),
			Args.of(40, 102334155)
		);
	}
}