/**
 * 
 */
package org.jaden.challenges;

import java.io.BufferedReader;
import java.util.Arrays;

/**
 * @author rshen
 * 
 */
public class VitosFamily extends Challenge {
	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader("test/vitosfamily_test");
		int numCases = Integer.parseInt(reader.readLine());
		
		for (int i = 0; i < numCases; i++) {
			String line = reader.readLine();
			String[] c = line.split(" ");
			int[] streets = new int[c.length - 1];
			for (int j = 1; j < c.length; j++) {
				streets[j - 1] = Integer.parseInt(c[j]);
			}
			Arrays.sort(streets);
			
			int median = streets[streets.length / 2];
			int sum = 0;
			for (int j = 0; j < streets.length / 2; j++) {
				sum += (median - streets[j]);
			}
			for (int j = streets.length / 2 + 1; j < streets.length; j++) {
				sum += (streets[j] - median);
			}
			System.out.println(sum);
		}
	}
}
