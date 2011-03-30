package org.jaden.challenges;

import java.io.BufferedReader;

/**
 * <h3>The Stern-Brocot Number System</h3>
 * <p>
 * PC/UVa IDs: 110507/10077, Popularity: C, Success rate: high Level: 1
 * </p>
 * 
 * @author rshen
 * 
 */
public class SternBrocot extends Challenge {
	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader("test/sternbrocot_test");
		String line = null;

		while ((line = reader.readLine()) != null && !line.trim().equals("1 1")) {
			String[] numbers = line.trim().split(" ");

			Pair pair = new Pair(Integer.parseInt(numbers[0]),
					Integer.parseInt(numbers[1]));
			Pair start = new Pair(1, 1);
			if (pair.compareTo(start) < 0) {
				solve(pair, start, 'N', new Pair(0, 1), new Pair(0, 1));
			} else {
				solve(pair, start, 'N', new Pair(1, 0), new Pair(1, 0));
			}
		}
	}

	private static void solve(Pair dest, Pair start, char last, Pair parent,
			Pair increment) {
		int c = dest.compareTo(start);

		if (c < 0) {
			System.out.print("L");
			if (last != 'L')
				increment = parent;
			solve(dest, new Pair(start.first + increment.first, start.second
					+ increment.second), 'L', start, increment);
		} else if (c > 0) {
			System.out.print("R");
			if (last != 'R')
				increment = parent;
			solve(dest, new Pair(start.first + increment.first, start.second
					+ increment.second), 'R', start, increment);
		} else {
			System.out.print("\n");
		}
	}

	private static class Pair implements Comparable<Pair> {
		public int first;

		public int second;

		public Pair(int first, int second) {
			this.first = first;
			this.second = second;
		}

		public int compareTo(Pair pair) {
			return this.first * pair.second - this.second * pair.first;
		}
	}
}
