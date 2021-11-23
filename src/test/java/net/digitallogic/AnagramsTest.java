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

class AnagramsTest {
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
					.map(args -> dynamicTest(args.getStringA(), () -> {
						boolean result = (boolean) m.invoke(new Anagrams(), args.getStringA(), args.getStringB());
						assertThat(result).isEqualTo(args.isExpected());
					}))
			));
	}

	Stream<Method> findTestMethods() {
		return Arrays.stream(Anagrams.class.getDeclaredMethods())
			.filter(m -> Modifier.isPublic(m.getModifiers()))
			.filter(m -> m.getAnnotation(Disabled.class) == null)
			.filter(m -> {
				Class<?>[] args = m.getParameterTypes();
				return args.length == 2 &&
					args[0] == String.class &&
					args[1] == String.class &&
					(m.getReturnType() == Boolean.TYPE || m.getReturnType() == Boolean.class);
			});
	}

	Stream<Args> testCases() {
		return Stream.of(
			Args.of("Arc", "Car", true),
			Args.of("Bat", "Rat", false),
			Args.of("D e b i t card","B a d credit", true),
			Args.of("hell", "hel", false),
			Args.of("car", "cars", false),
			Args.of("Dor.mit_ory", "D-irty room!", true),
			Args.of("Lis_ten!", "Silent?", true)
		);
	}

	@Value(staticConstructor = "of")
	private static class Args {
		String stringA;
		String stringB;
		boolean expected;
	}
}