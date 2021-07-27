package net.digitallogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Write a function that will take the elements of an array,
 * and group them into sub-arrays of the defined size.
 * Example
 * Input: ({1,2,3,4}, 2), Output: {{1,2},{3,4}}
 * Input: ({1,2,3,4,5,6},2), Output: {{1,2},{3,4},{5,6}}
 * Input: ({1,2,3,4,5},2), Output: {{1,2},{3,4},{5}}
 */

public class ArrayChunk {

	public static int[][] chuck(int[] ary, int size) {
		int rows = (int) Math.ceil(ary.length / (double)size);

		int[][] result = new int[rows][size];

		for (int y=0; y<rows; ++y) {
			result[y] = Arrays.copyOfRange(ary, (y * size), Math.min(y * size + size, ary.length));
		}

		return result;
	}

	public static int[][] listChunk(int[] ary, int size) {
		List<int[]> result = new ArrayList<>();

		for (int i=0; i<ary.length; i+=size) {
			result.add(Arrays.copyOfRange(ary, i, Math.min(i+size, ary.length)));
		}

		return result.toArray(int[][]::new);
	}
}
