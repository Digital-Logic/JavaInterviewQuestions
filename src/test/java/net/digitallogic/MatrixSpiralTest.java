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

class MatrixSpiralTest {
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
					.map(args -> dynamicTest(String.format("%dx%d", args.getN(), args.getN()), () -> {
						int[][] result = (int[][]) m.invoke(new MatrixSpiral(), args.getN());
						assertThat(result).isDeepEqualTo(args.getGrid());
					}))
			));
	}

	Stream<Method> findTestMethods() {
		return Arrays.stream(MatrixSpiral.class.getDeclaredMethods())
			.filter(m -> Modifier.isPublic(m.getModifiers()))
			.filter(m -> m.getAnnotation(Disabled.class) == null)
			.filter(m -> {
				Class<?>[] args = m.getParameterTypes();
				return args.length == 1 &&
					args[0] == Integer.TYPE &&
					m.getReturnType() == int[][].class;
			});
	}

	Stream<Args> testCases() {
		return Stream.of(
			Args.of(2, new int[][]{{1,2},{4,3}}),
			Args.of(3, new int[][]{{1,2,3},{8,9,4},{7,6,5}}),
			Args.of(4, new int[][]{{1,2,3,4},{12,13,14,5},{11,16,15,6},{10,9,8,7}}),
			Args.of(5, new int[][]{{1,2,3,4,5},{16,17,18,19,6},{15,24,25,20,7},{14,23,22,21,8},{13,12,11,10,9}})
		);
	}


	@Value(staticConstructor = "of")
	private static class Args {
		int n;
		int[][] grid;
	}

}