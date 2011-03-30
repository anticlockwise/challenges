/**
 * 
 */
package org.jaden.challenges;

import java.io.BufferedReader;

/**
 * <h3>The Archeologist’s Dilemma</h3>
 * <p>
 * PC/UVa IDs: 110503/701, Popularity: A, Success rate: low Level: 1
 * </p>
 * 
 * @author rshen
 * 
 */
public class ArcheologistDilemma extends Challenge {
	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader("test/archdilemma_test");
		String line = null;

		while ((line = reader.readLine()) != null) {
			long n = Long.parseLong(line.trim());
			int e = 1;
			long s = 2;
			int l = (int) Math.pow(10, line.trim().length() + 1);
			while (!found(n, s, l) && s < Long.MAX_VALUE) {
				s = s << 1;
				e++;
			}
			if (s <= Long.MAX_VALUE && found(n, s, l)) {
				System.out.println(e);
			} else {
				System.out.println("no power of 2");
			}
		}
	}

	private static boolean found(long n, long s, int l) {
		if (s / l == 0)
			return false;
		s /= l;
		while (s != 0) {
			if (s == n)
				return true;
			s /= 10;
		}
		return false;
	}
}
