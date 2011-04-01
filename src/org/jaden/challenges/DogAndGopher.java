/**
 * 
 */
package org.jaden.challenges;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author rshen
 * 
 */
public class DogAndGopher extends Challenge {
	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader("test/doggopher_test");
		String line = null;

		while ((line = reader.readLine()) != null) {
			if (line.trim().equals(""))
				continue;

			double[] gopherCoord = new double[2];
			double[] dogCoord = new double[2];
			double[][] holes;
			double[][] distance;

			String[] c = line.split(" ");
			int nHoles = Integer.parseInt(c[0]);
			holes = new double[nHoles][3];
			distance = new double[nHoles][3];
			gopherCoord[0] = Double.parseDouble(c[1]);
			gopherCoord[1] = Double.parseDouble(c[2]);
			dogCoord[0] = Double.parseDouble(c[3]);
			dogCoord[1] = Double.parseDouble(c[4]);

			for (int i = 0; i < nHoles; i++) {
				line = reader.readLine();
				c = line.split(" ");
				holes[i][0] = Double.parseDouble(c[0]);
				holes[i][1] = Double.parseDouble(c[1]);
				holes[i][2] = i;

				distance[i][0] = getDistance(holes[i], dogCoord);
				distance[i][1] = getDistance(holes[i], gopherCoord);
				distance[i][2] = i;
			}

			Arrays.sort(distance, new Comparator<double[]>() {
				public int compare(double[] a, double[] b) {
					if (a[0] > b[0]) {
						return -1;
					} else if (a[0] < b[0]) {
						return 1;
					} else {
						return 0;
					}
				}
			});

			int minI = -1;
			for (int i = 0; i < distance.length; i++) {
				if (distance[i][0] >= 2.0 * distance[i][1]) {
					if (distance[i][2] < minI || minI == -1)
						minI = (int) distance[i][2];
				}
			}

			if (minI == -1) {
				System.out.println("The gopher cannot escape.");
			} else {
				System.out.format("The gopher can escape through the hole at "
						+ "(%.3f, %.3f).", holes[minI][0], holes[minI][1]);
			}
		}
	}

	private static double getDistance(double[] hole, double[] a) {
		double xs = hole[0] - a[0];
		double ys = hole[1] - a[1];
		return Math.sqrt(xs * xs + ys * ys);
	}
}
