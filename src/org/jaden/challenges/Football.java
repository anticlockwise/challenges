/**
 * 
 */
package org.jaden.challenges;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h3>Football (aka Soccer)</h3>
 * <p>
 * PC/UVa IDs: 110408/10194, Popularity: B, Success rate: average Level: 1
 * </p>
 * 
 * @author rshen
 * 
 */
public class Football extends Challenge {
	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader("test/football_test");
		int numCases = Integer.parseInt(reader.readLine().trim());

		for (int i = 0; i < numCases; i++) {
			Map<String, Team> teams = new HashMap<String, Team>();
			String tournName = reader.readLine().trim();
			int numTeams = Integer.parseInt(reader.readLine().trim());
			for (int j = 0; j < numTeams; j++) {
				String teamName = reader.readLine().trim();
				teams.put(teamName, new Team(teamName));
			}

			int numTournaments = Integer.parseInt(reader.readLine().trim());
			for (int j = 0; j < numTournaments; j++) {
				String t = reader.readLine().trim();
				String[] sides = t.split("@");

				String[] t1 = sides[0].split("#");
				int goals1 = Integer.parseInt(t1[1]);
				String teamName1 = t1[0];

				String[] t2 = sides[1].split("#");
				int goals2 = Integer.parseInt(t2[0]);
				String teamName2 = t2[1];

				Team team1 = teams.get(teamName1);
				Team team2 = teams.get(teamName2);
				if (goals1 > goals2) {
					team1.win();
					team2.lose();
				} else if (goals1 < goals2) {
					team1.lose();
					team2.win();
				} else {
					team1.tie();
					team2.tie();
				}

				team1.scored(goals1);
				team1.against(goals2);
				team2.scored(goals2);
				team2.against(goals1);
			}

			List<Team> tList = new ArrayList<Team>(teams.values());
			Collections.sort(tList);

			System.out.println(tournName);
			int rank = 1;
			for (Team t : tList) {
				System.out.println(rank + ") " + t);
				rank++;
			}
			System.out.println();
		}
	}

	private static class Team implements Comparable<Team> {
		private int points;

		private String name;

		private int[] games = new int[3];

		private int[] goals = new int[2];

		public Team(String name) {
			this.name = name;
		}

		public void scored(int goals) {
			this.goals[0] += goals;
		}

		public void against(int goals) {
			this.goals[1] += goals;
		}

		public void win() {
			this.games[0] += 1;
			this.points += 3;
		}

		public void tie() {
			this.games[1] += 1;
			this.points += 1;
		}

		public void lose() {
			this.games[2] += 1;
		}

		public int getGoalDiff() {
			return this.goals[0] - this.goals[1];
		}

		public int gamesPlayed() {
			return this.games[0] + this.games[1] + this.games[2];
		}

		@Override
		public int compareTo(Team t) {
			if (this.points > t.points) {
				return -1;
			} else if (this.points < t.points) {
				return 1;
			} else {
				if (this.games[0] > t.games[0]) {
					return -1;
				} else if (this.games[0] < t.games[0]) {
					return 1;
				} else {
					if (this.getGoalDiff() > t.getGoalDiff()) {
						return -1;
					} else if (this.getGoalDiff() < t.getGoalDiff()) {
						return 1;
					} else {
						if (this.goals[0] > t.goals[0]) {
							return -1;
						} else if (this.goals[0] < t.goals[0]) {
							return 1;
						} else {
							if (this.gamesPlayed() > t.gamesPlayed()) {
								return 1;
							} else if (this.gamesPlayed() < t.gamesPlayed()) {
								return -1;
							} else {
								return this.name.compareTo(t.name);
							}
						}
					}
				}
			}
		}

		public String toString() {
			String result = String.format(
					"%s %dp, %dg (%d-%d-%d), %dgd (%d-%d)", name, points,
					this.gamesPlayed(), this.games[0], this.games[1],
					this.games[2], this.getGoalDiff(), this.goals[0],
					this.goals[1]);
			return result;
		}
	}
}
