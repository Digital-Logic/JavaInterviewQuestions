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

class PalindromeTest {
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
					.map(args -> dynamicTest(args.getStr(), () -> {
						boolean result = (boolean) m.invoke(new Palindrome(), args.getStr());
						assertThat(result).isEqualTo(args.isExpected());
					}))
			));
	}

	Stream<Method> findTestMethods() {
		return Arrays.stream(Palindrome.class.getDeclaredMethods())
			.filter(m -> Modifier.isPublic(m.getModifiers()))
			.filter(m -> m.getAnnotation(Disabled.class) == null)
			.filter(m -> {
				Class<?>[] args = m.getParameterTypes();
				return args.length == 1 &&
					args[0] == String.class &&
					(m.getReturnType() == Boolean.TYPE || m.getReturnType() == Boolean.class);
			});
	}

	Stream<Args> testCases() {
		return Stream.of(
			Args.of("hannah!", true),
			Args.of("dog", false),
			Args.of("I", true),
			Args.of("Mom", true),
			Args.of("Taco cat", true),
			Args.of("Amore, Roma.", true),
			Args.of("aa", true),
			Args.of("or", false)
		);
	}

	@Value(staticConstructor = "of")
	static class Args {
		String str;
		boolean expected;
	}
}