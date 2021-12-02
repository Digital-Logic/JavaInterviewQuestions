package net.digitallogic;

/**
 * Write a function that will return a NxN matrix that
 * spirals clockwise, starting at position [0][0] with the
 * value of 1, and ending at the value of n².
 *
 * Examples
 * 2 => {{1, 2},
 *       {4, 3}}
 *
 * 3 => {{1, 2, 3},
 *       {8, 9, 4},
 *       {7, 6, 5}}
 *
 * 4 => {{ 1,  2,  3, 4},
 *       {12, 13, 14, 5},
 *       {11, 16, 15, 6},
 *       {10,  9,  8, 7}}
 */
public class MatrixSpiral {

	public static int[][] matrixSpiral(int n) {
		final int[][] grid = new int[n][n];

		// Matrix barriers
		int top=0,
			left=0,
			right=n-1,
			bottom=n-1;

		int index=1;
		final int max = n*n;

		while(index<=max) {
			// Top Row
			for (int i=left; i<=right; ++i)
				grid[top][i] = index++;
			++top;

			// right Column
			for (int i=top; i<=bottom; ++i)
				grid[i][right] = index++;
			--right;

			// Bottom Row
			for (int i=right; i>=left; --i)
				grid[bottom][i] = index++;
			--bottom;

			// left Column
			for (int i=bottom; i>=top; --i)
				grid[i][left] = index++;
			++left;
		}

		return grid;
	}
}
