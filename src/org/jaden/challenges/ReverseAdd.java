/**
 * 
 */
package org.jaden.challenges;

import java.io.BufferedReader;

/**
 * <h3>Reverse and Add</h3>
 * <p>
 * PC/UVa IDs: 110502/10018, Popularity: A, Success rate: low Level: 1
 * </p>
 * 
 * @author rshen
 * 
 */
public class ReverseAdd extends Challenge {
	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader("test/reverseadd_test");
		int numCases = Integer.parseInt(reader.readLine());

		for (int i = 0; i < numCases; i++) {
			int num = Integer.parseInt(reader.readLine().trim());
			int rev = reverseNum(num);
			int its = 0;
			while (num != rev) {
				num += rev;
				rev = reverseNum(num);
				its++;
			}
			System.out.println(its + " " + num);
		}
	}

	private static int reverseNum(int n) {
		int r = 0;
		while (n != 0) {
			r = r * 10 + (n % 10);
			n /= 10;
		}
		return r;
	}
}
