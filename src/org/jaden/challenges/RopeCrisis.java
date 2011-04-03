/**
 * 
 */
package org.jaden.challenges;

import java.io.BufferedReader;

/**
 * @author rshen
 * 
 */
public class RopeCrisis extends Challenge {
	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader("test/ropecrisis_test");
		int nCases = Integer.parseInt(reader.readLine());

		int[] t1 = new int[2]; // Team one coordinates
		int[] t2 = new int[2]; // Team two coordinates
		int[] origin = new int[2];
		int r = 0; // Radius of pillar

		for (int i = 0; i < nCases; i++) {
			r = 0;
			for (int j = 0; j < 2; j++) {
				t1[j] = 0;
				t2[j] = 0;
			}

			String line = reader.readLine();
			String[] c = line.split(" ");
			for (int j = 0; j < 2; j++) {
				t1[j] = Integer.parseInt(c[j]);
				t2[j] = Integer.parseInt(c[j + 2]);
			}
			r = Integer.parseInt(c[4]);

			double d1 = distance(t1, origin);
			double d2 = distance(t2, origin);
			double d3 = distance(t1, t2);

			double x1 = tangent(d1, r);
			double x2 = tangent(d2, r);

			double cosAlpha = (d1 + d2 - d3)
					/ (2 * Math.sqrt(d1) * Math.sqrt(d2));
			double alpha = Math.acos(cosAlpha);
			double alpha1 = Math.acos(r / Math.sqrt(d1));
			double alpha2 = Math.acos(r / Math.sqrt(d2));
			double alphaX = alpha - alpha1 - alpha2;
			double x = r * alphaX;

			System.out.format("%.3f\n", x1 + x2 + x);
		}
	}

	private static double tangent(double d1, double r) {
		return Math.sqrt(d1 - r * r);
	}

	private static double distance(int[] from, int[] to) {
		double xs = from[0] - to[0];
		double ys = from[1] - to[1];
		return xs * xs + ys * ys;
	}
}
