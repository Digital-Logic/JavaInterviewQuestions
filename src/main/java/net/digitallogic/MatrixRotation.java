package net.digitallogic;

/**
 * 1. Write a function that will rotate an NxN matrix clockwise by 90 degrees.
 *    The matrix is guaranteed to be square.
 * Example
 * {{1,2}, => {{3,1},
 *  {3,4}}     {4,2}}
 *
 *  {{1,2,3},    {{7,4,1},
 *   {4,5,6}, =>  {8,5,2},
 *   {7,8,9}}     {9,6,3}}
 *
 *  2. Can you rotate the matrix in-place?
 **/

public class MatrixRotation {

	public static int[][] simpleRotate(int[][] grid) {
		int[][] result = new int[grid.length][grid[0].length];

		for (int y1=0, x2=grid.length-1; y1<grid.length && x2 >=0; ++y1, --x2) {
			for (int i=0; i<grid.length; ++i) {
				result[i][x2] = grid[y1][i];
			}
		}

		return result;
	}

	public static int[][] rotateInPlace(int[][] grid) {
		int width = grid.length-1;

		for (int l=0; l<grid.length/2; ++l) {
			for (int i=l; i<width - l; ++i){
				int temp = grid[l][i];

				grid[l][i] = grid[width-i][l];
				grid[width-i][l] = grid[width - l][width - i];
				grid[width-l][width - i] = grid[i][width - l];
				grid[i][width -l] = temp;
			}
		}
		return grid;
	}

	public static int[][] rotateInPlaceBuffer(int[][] grid) {

		int top=0,
			left=0,
			right=grid.length-1,
			bottom=grid[0].length-1;

		while (top < bottom) {
			int[] cache = new int[right-left];

			// store top row into cache
			for (int x=left, i=0; x<right && i<grid.length; ++x, ++i)
				cache[i] = grid[top][x];

			// copy left column into top row
			for (int x=left, y=bottom;x<right && y>top; ++x, --y)
				grid[top][x] = grid[y][left];

			// copy bottom row into left column
			for (int y=bottom, x=right; y>top && x>left; --y, --x)
				grid[y][left] = grid[bottom][x];

			// copy right column into bottom row;
			for (int x=right, y=top; y<bottom && x>left; ++y, x--)
				grid[bottom][x] = grid[y][right];

			// copy cache into right column
			for (int y=top, i=0; i< cache.length && y < bottom; ++y, ++i)
				grid[y][right] = cache[i];

			++top; ++left; --right; --bottom;
		}
		return grid;
	}
}
