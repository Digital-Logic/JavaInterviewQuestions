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
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class ReverseIntTest {
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
					.map(args -> dynamicTest(String.format("Reverse %d", args.getN()), () -> {
						int result = (int) m.invoke(new ReverseInt(), args.getN());
						assertThat(result).isEqualTo(args.getExpected());
					}))
			));
	}

	Stream<Method> findTestMethods() {
		return Arrays.stream(ReverseInt.class.getDeclaredMethods())
			.filter(m -> Modifier.isPublic(m.getModifiers()))
			.filter(m -> m.getAnnotation(Disabled.class) == null)
			.filter(m -> {
				Class<?>[] args = m.getParameterTypes();
				return args.length == 1 &&
					args[0] == Integer.TYPE &&
					m.getReturnType() == Integer.TYPE;
			});
	}

	Stream<Args> testCases() {
		return Stream.of(
			Args.of(123, 321),
			Args.of(450, 54),
			Args.of(-763, -367),
			Args.of(-20, -2)
		);
	}

	@Value(staticConstructor = "of")
	private static class Args {
		int n;
		int expected;
	}
}