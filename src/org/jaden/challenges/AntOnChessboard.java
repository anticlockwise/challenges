/**
 * 
 */
package org.jaden.challenges;

import java.io.BufferedReader;

/**
 * @author rshen
 * 
 */
public class AntOnChessboard extends Challenge {
	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader("test/chessboardant_test");
		String line = null;

		while ((line = reader.readLine()) != null && !line.equals("0")) {
			long n = Long.parseLong(line);
			long s = 0;
			long i = 0;
			long mid = 1;
			while (s < n) {
				s += i * 2 + 1;
				mid += i * 2;
				i++;
			}

			long row, col;
			if (i % 2 == 0) {
				if (n >= mid) {
					col = i;
					row = s - n + 1;
				} else {
					row = i;
					col = i - (mid - n);
				}
			} else {
				if (n >= mid) {
					row = i;
					col = s - n + 1;
				} else {
					col = i;
					row = i - (mid - n);
				}
			}

			System.out.println(col + " " + row);
		}
	}
}
