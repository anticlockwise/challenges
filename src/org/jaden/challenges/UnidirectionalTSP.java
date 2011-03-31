/**
 * 
 */
package org.jaden.challenges;

import java.io.BufferedReader;
import java.util.Stack;

/**
 * @author rshen
 * 
 */
public class UnidirectionalTSP extends Challenge {
	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader("test/unitsp_test");
		String line = null;

		while ((line = reader.readLine()) != null) {
			String[] c = line.split(" ");
			int rows = Integer.parseInt(c[0]);
			int cols = Integer.parseInt(c[1]);

			int[][] grid = new int[rows][cols];
			int[][] dp = new int[rows][cols];
			int[][] parent = new int[rows][cols];
			int[][] dirs = { { -1, 1 }, { 0, 1 }, { 1, 1 } };
			for (int i = 0; i < rows; i++) {
				line = reader.readLine().trim();
				String[] nums = line.split(" ");
				for (int j = 0; j < cols; j++) {
					grid[i][j] = Integer.parseInt(nums[j]);
					dp[i][j] = Integer.MAX_VALUE;
					parent[i][j] = -1;
				}
			}

			dp[0][0] = grid[0][0];
			int minDistance = 0;
			int minIndex = -1;
			for (int i = 0; i < cols - 1; i++) {
				int minD = Integer.MAX_VALUE;
				int minI = Integer.MAX_VALUE;
				for (int j = 0; j < rows; j++) {
					for (int k = 0; k < dirs.length; k++) {
						int[] d = dirs[k];
						int col = i + d[1];
						int row = j + d[0];
						if (row < 0) {
							row = rows - 1;
						} else if (row > rows - 1) {
							row = 0;
						}

						if (dp[j][i] != Integer.MAX_VALUE) {
							int distance = grid[row][col] + dp[j][i];
							if (distance < dp[row][col]) {
								dp[row][col] = distance;
								parent[row][col] = j;
							}
							if (distance < minD) {
								minD = distance;
								minI = row;
							} else if (distance == minD && minI > row) {
								minD = distance;
								minI = row;
							}
						}
					}
				}
				minDistance = minD;
				minIndex = minI;
			}

			Stack<Integer> stack = new Stack<Integer>();
			stack.push(minIndex);
			for (int i = cols - 1; i >= 1; i--) {
				minIndex = parent[minIndex][i];
				stack.push(minIndex);
			}
			while (!stack.isEmpty()) {
				System.out.print((stack.pop() + 1) + " ");
			}
			System.out.print("\n");
			System.out.println(minDistance);
		}
	}
}
