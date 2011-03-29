package org.jaden.challenges;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h3>Contest Scoreboard</h3>
 * <p>
 * PC/UVa IDs: 110207/10258, Popularity: B, Success rate: average Level: 1
 * </p>
 * 
 * @author rshen
 */
public class ContestScoreboard extends Challenge {

	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader(args[0]);
		int numCases = Integer.parseInt(reader.readLine());
		reader.readLine();
		String line = null;

		for (int i = 0; i < numCases; i++) {
			Map<Integer, Contestant> contestants = new HashMap<Integer, Contestant>();
			while ((line = reader.readLine()) != null
					&& !line.trim().equals("")) {
				String[] cons = line.split(" ");
				int number = Integer.parseInt(cons[0]);
				int problem = Integer.parseInt(cons[1]);
				int time = Integer.parseInt(cons[2]);
				String l = cons[3];

				Contestant c = null;
				if (!contestants.containsKey(number)) {
					c = new Contestant(number);
					contestants.put(number, c);

				} else {
					c = contestants.get(number);
				}

				if (l.equals("I")) {
					c.addPenalty(20);
				} else if (l.equals("C")) {
					c.addPenalty(time);
					c.setSolved(problem);
				}
			}

			List<Contestant> cc = new ArrayList<Contestant>(
					contestants.values());
			Collections.sort(cc);
			for (Contestant c : cc) {
				System.out.println(c.number + " " + c.solved + " " + c.penalty);
			}
		}
	}

	private static class Contestant implements Comparable {

		private int[] problems = new int[9];

		private int solved;

		private int penalty;

		private int number;

		public Contestant(int number) {
			this.number = number;
		}

		public int getPenalty() {
			return penalty;
		}

		public void setPenalty(int penalty) {
			this.penalty = penalty;
		}

		public void addPenalty(int penalty) {
			this.penalty += penalty;
		}

		public int getSolved() {
			return solved;
		}

		public void setSolved(int solved) {
			if (problems[solved - 1] == 0) {
				this.solved++;
				problems[solved - 1] = 1;
			}
		}

		@Override
		public int compareTo(Object t) {
			if (t == null) {
				return -1;
			}

			Contestant c = (Contestant) t;
			if (this.solved > c.solved) {
				return -1;
			} else if (this.solved < c.solved) {
				return 1;
			} else {
				if (this.penalty < c.penalty) {
					return -1;
				} else if (this.penalty > c.penalty) {
					return 1;
				} else {
					if (this.number > c.number) {
						return 1;
					} else if (this.number < c.number) {
						return -1;
					} else {
						return 0;
					}
				}
			}
		}

	}
}
