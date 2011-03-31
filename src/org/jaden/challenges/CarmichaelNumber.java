/**
 * 
 */
package org.jaden.challenges;

import java.io.BufferedReader;

/**
 * @author rshen
 * 
 */
public class CarmichaelNumber extends Challenge {
	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader("test/carmichael_test");
		String line = null;

		while ((line = reader.readLine()) != null && !line.trim().equals("0")) {
			int n = Integer.parseInt(line.trim());
			int a = 2;
			boolean carmichael = true;
			while (a < n) {
				System.out.println(Math.pow(a % n, n));
				if (((int) Math.pow(a % n, n)) % n == a) {
					a++;
				} else {
					carmichael = false;
					break;
				}
			}

			if (carmichael) {
				System.out.format("The number %d is a Carmichael number.\n", n);
			} else {
				System.out.format("%d is normal.\n", n);
			}
		}
	}
}
