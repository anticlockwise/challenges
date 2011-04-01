/**
 * 
 */
package org.jaden.challenges;

import java.io.BufferedReader;

/**
 * @author rshen
 * 
 */
public class KnightsOfRoundTable extends Challenge {
	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader("test/rtknights_test");
		String line = null;

		while ((line = reader.readLine()) != null) {
			String[] sides = line.split(" ");
			double sideA = Double.parseDouble(sides[0]);
			double sideB = Double.parseDouble(sides[1]);
			double sideC = Double.parseDouble(sides[2]);

			// Calculate the semi-perimeter
			double s = (sideA + sideB + sideC) / 2.0;
			double radius = Math.sqrt((s - sideA) * (s - sideB) * (s - sideC)
					/ s);
			System.out.format("The radius of the round table is: %.3f", radius);
		}
	}
}
