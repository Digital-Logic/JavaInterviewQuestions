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

class ArrayChunkTest {
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
						int[][] result = (int[][]) m.invoke(new ArrayChunk(),args.getAry(), args.getSize());
						assertThat(result).isDeepEqualTo(args.getExpected());
					}))
			));
	}

	Stream<Method> findTestMethods() {
		return Arrays.stream(ArrayChunk.class.getDeclaredMethods())
			.filter(m -> Modifier.isPublic(m.getModifiers()))
			.filter(m -> m.getAnnotation(Disabled.class) == null)
			.filter(m -> {
				Class<?>[] args = m.getParameterTypes();
				return args.length == 2 &&
					args[0] == int[].class &&
					args[1] == Integer.TYPE &&
					m.getReturnType() == int[][].class;
			});
	}

	@Value(staticConstructor = "of")
	private static class Args {
		String name;
		int[] ary;
		int size;
		int[][] expected;
	}

	Stream<Args> testCases() {
		return Stream.of(
			Args.of(
				"2x2",
				new int[]{1, 2, 3, 4},
				2,
				new int[][]{{1, 2}, {3, 4}}

			),
			Args.of(
				"3x2",
				new int[]{1,2,3,4,5,6},
				2,
				new int[][]{{1,2},{3,4},{5,6}}
			),
			Args.of(
				"4x3",
				new int[]{1,2,3,4,5,6,7,8,9,10,11,12},
				3,
				new int[][]{{1,2,3},{4,5,6},{7,8,9},{10,11,12}}
			),
			Args.of(
				"4x3-part",
				new int[]{1,2,3,4,5,6,7,8,9,10},
				3,
				new int[][]{{1,2,3},{4,5,6},{7,8,9},{10}}
			)
		);
	}
}