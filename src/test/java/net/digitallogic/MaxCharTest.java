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

class MaxCharTest {

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
						char result;
						if (m.getReturnType() == String.class) {
							String temp = (String) m.invoke(new MaxChar(), args.getStr());
							assertThat(temp).hasSize(1);
							result = temp.charAt(0);
						}
						else result = (char) m.invoke(new MaxChar(), args.getStr());

						assertThat(result).isEqualTo(args.getExpected());
					}))
			));
	}


	Stream<Method> findTestMethods() {
		return Arrays.stream(MaxChar.class.getDeclaredMethods())
			.filter(m -> Modifier.isPublic(m.getModifiers()))
			.filter(m -> m.getAnnotation(Disabled.class) == null)
			.filter(m -> {
				Class<?>[] args = m.getParameterTypes();
				return args.length == 1 &&
					args[0] == String.class &&
					(m.getReturnType() == String.class || m.getReturnType() == Character.TYPE);

			});
	}

	@Value(staticConstructor = "of")
	static class Args {
		String str;
		char expected;
	}

	Stream<Args> testCases() {
		return Stream.of(
			Args.of(
				"aggressiveness",
				's'
			),
			Args.of(
				"hello.....world",
				'l'
			),
			Args.of(
				"Hello      WORLD",
				'l'
			),
			Args.of(
				"Cat in THE hat!",
				't'
			)
		);
	}
}