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

class VowelsTest {
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
						int result = (int) m.invoke(new Vowels(), args.getStr());
						assertThat(result).isEqualTo(args.expected);
					}))
			));
	}

	Stream<Method> findTestMethods() {
		return Arrays.stream(Vowels.class.getDeclaredMethods())
			.filter(m -> Modifier.isPublic(m.getModifiers()))
			.filter(m -> m.getAnnotation(Disabled.class) == null)
			.filter(m -> {
				Class<?>[] args = m.getParameterTypes();
				return args.length == 1 &&
					args[0] == String.class &&
					m.getReturnType() == Integer.TYPE;
			});
	}

	Stream<Args> testCases() {
		return Stream.of(
			Args.of("Hello World!", 3),
			Args.of("Apples are red.", 5),
			Args.of("U.are-not_alone!", 7),
			Args.of("YOU>ARE<NOT_ALONE!!!.", 8),
			Args.of("Why?", 0)
		);
	}

	@Value(staticConstructor = "of")
	private static class Args {
		String str;
		int expected;
	}
}