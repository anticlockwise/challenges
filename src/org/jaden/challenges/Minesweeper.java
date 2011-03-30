package org.jaden.challenges;

import java.io.*;

/**
 * <h3>Minesweeper</h3>
 * <p>
 * PC/UVa IDs: 110102/10189, Popularity: A, Success rate: high Level: 1
 * </p>
 * 
 * @author rshen
 */
public class Minesweeper extends Challenge {
	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader(args[0]);
		String line = null;
		while ((line = reader.readLine()) != null && !line.equals("0 0")) {
			String[] config = line.split(" ");
			int rows = Integer.parseInt(config[0]);
			int cols = Integer.parseInt(config[1]);

			int[][] board = new int[rows][cols];
			for (int i = 0; i < rows; i++) {
				line = reader.readLine();
				char[] mines = line.trim().toCharArray();

				for (int j = 0; j < mines.length; j++) {
					if (mines[j] == '*') {
						board[i][j] = -1;
						addMine(board, i, j, rows, cols);
					}
				}
			}

			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					System.out.print(board[i][j] == -1 ? "*" : board[i][j]);
				}
				System.out.print("\n");
			}
		}
	}

	// x and y are mine positions
	private static void addMine(int[][] board, int x, int y, int rows, int cols) {
		int[][] directions = { { 0, 1 }, { 0, -1 }, { -1, 0 }, { -1, -1 },
				{ -1, 1 }, { 1, 0 }, { 1, -1 }, { 1, 1 } };

		for (int i = 0; i < directions.length; i++) {
			int[] dir = directions[i];
			int x1 = x + dir[0];
			int y1 = y + dir[1];
			if (x1 >= 0 && y1 >= 0 && x1 < rows && y1 < cols) {
				if (board[x1][y1] != -1) {
					board[x1][y1] += 1;
				}
			}
		}
	}
}