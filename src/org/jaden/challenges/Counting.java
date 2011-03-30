/**
 * 
 */
package org.jaden.challenges;

import java.io.BufferedReader;

/**
 * @author rshen
 * 
 */
public class Counting extends Challenge {
	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader("test/counting_test");
		String line = null;

		while ((line = reader.readLine()) != null) {
			long[] f = new long[1001];
			f[1] = 2;
			f[2] = 5;
			f[3] = 13;

			int num = Integer.parseInt(line.trim());
			if (num <= 3)
				System.out.println(f[num]);
			else {
				int e = 3;
				while (e != num) {
					f[e + 1] = f[e] + f[e] + f[e - 1] + f[e - 2];
					e++;
				}
				System.out.println(f[e]);
			}
		}
	}
}
