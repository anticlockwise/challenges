/**
 * 
 */
package org.jaden.challenges;

import java.io.BufferedReader;

/**
 * <h3>Primary Arithmetic</h3>
 * <p>
 * PC/UVa IDs: 110501/10035, Popularity: A, Success rate: average Level: 1
 * </p>
 * 
 * @author rshen
 * 
 */
public class PrimaryArithmetic extends Challenge {
	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader("test/pa_test");
		String line = null;

		while ((line = reader.readLine()) != null && !line.trim().equals("0 0")) {
			String[] numbers = line.split(" ");
			int[] n1 = new int[10];
			int[] n2 = new int[10];
			for (int i = 0; i < n1.length; i++) {
				n1[i] = -1;
				n2[i] = -1;
			}

			char[] n = numbers[0].toCharArray();
			fillNumber(n, n1);
			n = numbers[1].toCharArray();
			fillNumber(n, n2);

			int numCarries = 0;
			int carry = 0;
			for (int i = n1.length - 1; i >= 0 && n1[i] != -1 && n2[i] != -1; i--) {
				if (n1[i] + n2[i] + carry >= 10) {
					numCarries += 1;
					carry = 1;
				} else {
					carry = 0;
				}
			}

			if (numCarries == 0) {
				System.out.println("No carry operation.");
			} else if (numCarries == 1) {
				System.out.println("1 carry operation.");
			} else {
				System.out.println(numCarries + " carry operations.");
			}
		}
	}

	private static void fillNumber(char[] n, int[] l) {
		int k = 9;
		for (int i = n.length - 1; i >= 0; i--) {
			l[k--] = Character.digit(n[i], 10);
		}
	}
}
