/**
 * 
 */
package org.jaden.challenges;

import java.io.BufferedReader;

/**
 * @author rshen
 * 
 */
public class MoreLight extends Challenge {
	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader("test/morelight_test");
		String line = null;

		while ((line = reader.readLine()) != null && !line.equals("0")) {
			int n = Integer.parseInt(line.trim());
			int nFactors = 2;
			double sqrt = Math.sqrt(n);
			for (int i = 2; i < sqrt; i++) {
				if (n % i == 0) {
					nFactors += 2;
				}
			}
			if (Math.ceil(sqrt) == sqrt) {
				nFactors++;
			}

			if (nFactors % 2 == 0) {
				System.out.println("no");
			} else {
				System.out.println("yes");
			}
		}
	}
}
