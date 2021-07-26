package net.digitallogic;

import lombok.Value;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class FizzBuzzTest {
	@Test
	void AtLestOneSolutionProvided() {
		assertThat(
		// Disable testing collections
		//	Stream.concat(
		//		findTestMethodsStringCollection(),
				findTestMethodsStringArray()
		//	)
		).describedAs("No solutions found to test.")
			.hasAtLeastOneElementOfType(Method.class);
	}

	@TestFactory
	@SuppressWarnings("unchecked cast")
	Stream<DynamicNode> dynamicTests() {
		return
//          Disable Testing Collections
//			Stream.concat(
//			findTestMethodsStringCollection()
//				.map(m -> dynamicContainer(m.getName(),
//					testCases()
//						.map(args -> dynamicTest(args.getName(), () -> {
//							Collection<String> result = (Collection<String>) m.invoke(new FizzBuzz(), args.getN());
//							assertThat(result)
//								.containsSequence(args.getExpected());
//						}))
//				)),
			findTestMethodsStringArray()
				.map(m -> dynamicContainer(m.getName(),
					testCases()
						.map(args -> dynamicTest(args.getName(), () -> {
							String[] result = (String[]) m.invoke(new FizzBuzz(), args.getN());
							assertThat(result)
								.containsExactly(args.getExpected().toArray(String[]::new));
						}))
				)
		);
	}

	Stream<Method> findTestMethodsStringCollection() {
		return Arrays.stream(FizzBuzz.class.getDeclaredMethods())
			.filter(m -> Modifier.isPublic(m.getModifiers()))
			.filter(m -> m.getAnnotation(Disabled.class) == null)
			.filter(m -> {
				Class<?>[] args = m.getParameterTypes();

				if (args.length == 1 &&
					args[0] == Integer.TYPE &&
					Collection.class.isAssignableFrom( m.getReturnType()))
				{
					if (m.getGenericReturnType() instanceof ParameterizedType) {
						ParameterizedType type = (ParameterizedType) m.getGenericReturnType();
						Type[] typeArgs = type.getActualTypeArguments();
						return (typeArgs.length == 1 &&
							typeArgs[0] == String.class);
					}
				}

				return false;
			});
	}

	Stream<Method> findTestMethodsStringArray() {
		return Arrays.stream(FizzBuzz.class.getDeclaredMethods())
			.filter(m -> Modifier.isPublic(m.getModifiers()))
			.filter(m -> m.getAnnotation(Disabled.class) == null)
			.filter(m -> {
				Class<?>[] args = m.getParameterTypes();
				return args.length == 1 &&
					args[0] == Integer.TYPE &&
					m.getReturnType() == String[].class;
			});
	}

	@Value(staticConstructor = "of")
	private static class Args {
		String name;
		int n;
		List<String> expected;
	}

	Stream<Args> testCases() {
		return Stream.of(
			Args.of(
				"FizzBuzz(3)",
				3,
				List.of(
					"1", "2", "fizz"
				)
			),
			Args.of(
				"FizzBuzz(6)",
				6,
				List.of(
					"1", "2", "fizz","4","buzz","fizz"
				)
			),
			Args.of(
				"FizzBuzz(15)",
				15,
				List.of(
					"1", "2", "fizz","4","buzz","fizz","7","8","fizz","buzz","11","fizz","13","14","fizzbuzz"
				)
			),
			Args.of(
				"FizzBuzz(30)",
				30,
				List.of(
					"1", "2", "fizz","4","buzz","fizz","7","8","fizz","buzz","11","fizz","13","14","fizzbuzz",
					"16","17","fizz","19","buzz","fizz","22","23","fizz","buzz","26","fizz","28","29","fizzbuzz"
				)
			)
		);
	}
}