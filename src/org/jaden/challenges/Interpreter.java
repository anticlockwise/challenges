package org.jaden.challenges;

import java.io.BufferedReader;

/**
 * <h3>Interpreter</h3>
 * <p>
 * PC/UVa IDs: 110106/10033, Popularity: B, Success rate: low Level: 2
 * </p>
 * 
 * @author rshen
 */
public class Interpreter extends Challenge {

	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader(args[0]);
		int numCases = Integer.parseInt(reader.readLine());
		reader.readLine();

		for (int i = 0; i < numCases; i++) {
			String line = null;
			int[] ram = new int[1000];
			int[] registers = new int[10];

			int n = 0;
			while ((line = reader.readLine()) != null
					&& !line.trim().equals("")) {
				int instruction = Integer.parseInt(line);
				ram[n++] = instruction;
			}

			int numExecuted = 0;
			int index = 0;
			int curInstruction = ram[index];
			while (curInstruction != 100) {
				int s = curInstruction % 10;
				int d = (curInstruction / 10) % 10;
				int command = (curInstruction / 100) % 10;
				switch (command) {
				case 2:
					registers[d] = s % 1000;
					index++;
					break;
				case 3:
					registers[d] += s;
					registers[d] %= 1000;
					index++;
					break;
				case 4:
					registers[d] *= s;
					registers[d] %= 1000;
					index++;
					break;
				case 5:
					registers[d] = registers[s] % 1000;
					index++;
					break;
				case 6:
					registers[d] += registers[s];
					registers[d] %= 1000;
					index++;
					break;
				case 7:
					registers[d] *= registers[s];
					registers[d] %= 1000;
					index++;
					break;
				case 8:
					registers[d] = ram[registers[s]];
					registers[d] %= 1000;
					index++;
					break;
				case 9:
					ram[registers[s]] = registers[d] % 1000;
					index++;
					break;
				case 0:
					if (registers[s] != 0) {
						index = registers[d];
					} else {
						index++;
					}
					break;
				}
				numExecuted++;
				curInstruction = ram[index];
			}
			numExecuted++;
			System.out.println(numExecuted);
		}
	}

}
