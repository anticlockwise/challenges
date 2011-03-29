package org.jaden.challenges;

import java.io.BufferedReader;

/**
 * <h3>Graphical Editor</h3>
 * <p>
 * PC/UVa IDs: 110105/10267, Popularity: B, Success rate: low Level: 1
 * </p>
 * 
 * @author rshen
 */
public class GraphicalEditor extends Challenge {

	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader(args[0]);
		String line = null;

		char[][] canvas = null;
		int x, y, y1, y2, x1, x2;
		while ((line = reader.readLine()) != null && !line.equals("X")) {
			String[] commands = line.split(" ");

			char command = commands[0].charAt(0);
			switch (command) {
			case 'I':
				int m = Integer.parseInt(commands[1]);
				int n = Integer.parseInt(commands[2]);
				canvas = new char[m][n];
				clear(canvas);
				break;
			case 'C':
				clear(canvas);
				break;
			case 'L':
				x = Integer.parseInt(commands[1]);
				y = Integer.parseInt(commands[2]);
				canvas[y - 1][x - 1] = commands[3].charAt(0);
				break;
			case 'V':
				x = Integer.parseInt(commands[1]);
				y1 = Integer.parseInt(commands[2]);
				y2 = Integer.parseInt(commands[3]);
				for (int i = y1 - 1; i <= y2 - 1; i++) {
					canvas[i][x - 1] = commands[4].charAt(0);
				}
				break;
			case 'H':
				x1 = Integer.parseInt(commands[1]);
				x2 = Integer.parseInt(commands[2]);
				y = Integer.parseInt(commands[3]);
				for (int i = x1 - 1; i <= x2 - 1; i++) {
					canvas[y - 1][i] = commands[4].charAt(0);
				}
				break;
			case 'K':
				x1 = Integer.parseInt(commands[1]);
				y1 = Integer.parseInt(commands[2]);
				x2 = Integer.parseInt(commands[3]);
				y2 = Integer.parseInt(commands[4]);
				for (int i = y1 - 1; i <= y2 - 1; i++) {
					for (int j = x1 - 1; j <= x2 - 1; j++) {
						canvas[i][j] = commands[5].charAt(0);
					}
				}
				break;
			case 'F':
				x = Integer.parseInt(commands[1]);
				y = Integer.parseInt(commands[2]);
				char pixel = canvas[y - 1][x - 1];
				for (int i = 0; i < canvas.length; i++) {
					for (int j = 0; j < canvas[i].length; j++) {
						if (canvas[i][j] == pixel) {
							canvas[i][j] = commands[3].charAt(0);
						}
					}
				}
				canvas[y - 1][x - 1] = commands[3].charAt(0);
				break;
			case 'S':
				System.out.println(commands[1]);
				for (int i = 0; i < canvas.length; i++) {
					for (int j = 0; j < canvas[i].length; j++) {
						System.out.print(canvas[i][j]);
					}
					System.out.print("\n");
				}
				break;
			}
		}
	}

	private static void clear(char[][] table) {
		if (table != null) {
			for (int i = 0; i < table.length; i++) {
				for (int j = 0; j < table[i].length; j++) {
					table[i][j] = 'o';
				}
			}
		}
	}

}
