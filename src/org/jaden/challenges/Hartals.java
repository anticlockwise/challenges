package org.jaden.challenges;

import java.io.BufferedReader;

/**
 * <h3>Hartals</h3>
 * <p>
 * PC/UVa IDs: 110203/10050, Popularity: B, Success rate: high Level: 2
 * </p>
 * 
 * @author rshen
 */
public class Hartals extends Challenge {

	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader(args[0]);
		Integer numTestCases = Integer.parseInt(reader.readLine());

		for (int i = 0; i < numTestCases; i++) {
			int n = Integer.parseInt(reader.readLine());
			int p = Integer.parseInt(reader.readLine());
			int[] days = new int[n + 1];
			for (int j = 0; j < p; j++) {
				String line = reader.readLine();
				int h = Integer.parseInt(line);
				int hs = h;
				while (hs <= n) {
					if ((hs + 1) % 7 != 0 && hs % 7 != 0) {
						days[hs] = 1;
					}
					hs += h;
				}
			}

			int sum = 0;
			for (int k = 0; k < days.length; k++) {
				sum += days[k];
			}
			System.out.println(sum);
		}
	}

}
