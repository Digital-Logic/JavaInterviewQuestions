package net.digitallogic;

import lombok.Value;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class ReverseStringTest {

	@Test
	void AtLestOneSolutionProvided() {
		assertThat(
			findTestMethods()
		).describedAs("No solutions found to test.")
			.hasAtLeastOneElementOfType(Method.class);
	}

	@TestFactory
	Stream<DynamicNode> dynamicTests() {
		return findTestMethods()
			.map(m -> dynamicContainer(m.getName(),
				testCases()
					.map(args -> dynamicTest("Reverse: " + args.getStr(), () -> {
						String result = (String) m.invoke(new ReverseString(), args.getStr());
						assertThat(result)
							.isEqualTo(args.getExpected());
					}))
			));
	}

	Stream<Method> findTestMethods() {
		return Arrays.stream(ReverseString.class.getDeclaredMethods())
			.filter(m -> Modifier.isPublic(m.getModifiers()))
			.filter(m -> m.getAnnotation(Disabled.class) == null)
			.filter(m -> {
				Class<?>[] args = m.getParameterTypes();
				return args.length == 1 &&
					args[0] == String.class &&
					m.getReturnType() == String.class;
			});
	}

	@Value(staticConstructor = "of")
	private static class Args {
		String str;
		String expected;
	}

	Stream<Args> testCases() {
		return Stream.of(
			Args.of("car", "rac"),
			Args.of("dog", "god"),
			Args.of("category", "yrogetac"),
			Args.of("hello world", "dlrow olleh"),
			Args.of("abcdefghijklmnopqrstuvwxyz", "zyxwvutsrqponmlkjihgfedcba")
		);
	}
}