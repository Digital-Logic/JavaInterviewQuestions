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

class MatrixRotationTest {

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
					.map(args -> dynamicTest(args.getName(), () -> {
						int[][] result = (int[][]) m.invoke(new MatrixRotation(), (Object) args.getGrid());
						assertThat(result).isDeepEqualTo(args.getExpected());
					}))
			));
	}

	Stream<Method> findTestMethods() {
		return Arrays.stream(MatrixRotation.class.getDeclaredMethods())
			.filter(m -> Modifier.isPublic(m.getModifiers()))
			.filter(m -> m.getAnnotation(Disabled.class) == null)
			.filter(m -> {
				Class<?>[] args = m.getParameterTypes();
				return args.length == 1 &&
					args[0] == int[][].class &&
					m.getReturnType() == int[][].class;
			});
	}

	Stream<Args> testCases() {
		return Stream.of(
			Args.of("2x2", new int[][]{{1,2},{3,4}}, new int[][]{{3,1},{4,2}}),
			Args.of("3x3", new int[][]{{1,2,3},{4,5,6},{7,8,9}}, new int[][]{{7,4,1},{8,5,2},{9,6,3}}),
			Args.of("4x4",
				new int[][]{{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}},
				new int[][]{{13,9,5,1},{14,10,6,2},{15,11,7,3},{16,12,8,4}}),
			Args.of("5x5",
				new int[][]{{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15},{16,17,18,19,20},{21,22,23,24,25}},
				new int[][]{{21,16,11,6,1},{22,17,12,7,2},{23,18,13,8,3},{24,19,14,9,4},{25,20,15,10,5}}
			),
			Args.of("6x6",
				new int[][]{{1,2,3,4,5,6},{7,8,9,10,11,12},{13,14,15,16,17,18},{19,20,21,22,23,24},{25,26,27,28,29,30},{31,32,33,34,35,36}},
				new int[][]{{31,25,19,13,7,1},{32,26,20,14,8,2},{33,27,21,15,9,3},{34,28,22,16,10,4},{35,29,23,17,11,5},{36,30,24,18,12,6}})
		);
	}

	@Value(staticConstructor = "of")
	private static class Args {
		String name;
		int[][] grid;
		int[][] expected;
	}
}