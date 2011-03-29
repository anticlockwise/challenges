package org.jaden.challenges;

import java.io.BufferedReader;

/**
 * <h3>Jolly Jumpers</h3>
 * <p>
 * PC/UVa IDs: 110201/10038, Popularity: A, Success rate: average Level: 1
 * </p>
 * 
 * @author rshen
 */
public class JollyJumpers extends Challenge {

	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader(args[0]);
		String line = null;

		while ((line = reader.readLine()) != null) {
			String[] numbers = line.split(" ");
			int num = Integer.parseInt(numbers[0]);
			boolean[] contains = new boolean[num - 1];

			for (int i = 2; i < numbers.length; i++) {
				int p = Integer.parseInt(numbers[i - 1]);
				int n = Integer.parseInt(numbers[i]);
				int index = Math.abs(p - n);
				if (index < contains.length) {
					contains[Math.abs(p - n) - 1] = true;
				}
			}

			String result = "Jolly";
			for (int i = 0; i < contains.length; i++) {
				if (!contains[i]) {
					result = "Not Jolly";
				}
			}

			System.out.println(result);
		}
	}

}
