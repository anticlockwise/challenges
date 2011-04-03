/**
 * 
 */
package org.jaden.challenges;

import java.io.BufferedReader;

/**
 * @author rshen
 * 
 */
public class TheTouristGuide extends Challenge {
	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader("test/ttg_test");
		String line = null;
		int index = 1;

		while ((line = reader.readLine()) != null && !line.equals("0 0")) {
			String[] c = line.split(" ");
			int nCities = Integer.parseInt(c[0]);
			int nRoads = Integer.parseInt(c[1]);

			int[][] dp = new int[nCities + 1][nCities + 1];
			for (int i = 0; i < nRoads; i++) {
				line = reader.readLine();
				c = line.split(" ");
				int from = Integer.parseInt(c[0]);
				int to = Integer.parseInt(c[1]);
				int capacity = Integer.parseInt(c[2]);
				dp[from][to] = capacity;
				dp[to][from] = capacity;
			}

			for (int i = 1; i <= nCities; i++) {
				for (int s = 1; s <= nCities; s++) {
					for (int t = 1; t <= nCities; t++) {
						dp[s][t] = Math.max(dp[s][t],
								Math.min(dp[s][i], dp[i][t]));
					}
				}
			}

			line = reader.readLine();
			c = line.split(" ");
			int source = Integer.parseInt(c[0]);
			int dest = Integer.parseInt(c[1]);
			int nPeople = Integer.parseInt(c[2]);

			int minTrips = (int) Math.ceil((double) nPeople / dp[source][dest]);

			System.out.format("Scenario #%d\n", index++);
			System.out.format("Minimum number of Trips = %d\n\n", minTrips);
		}
	}
}
