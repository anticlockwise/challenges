/**
 * 
 */
package org.jaden.challenges;

import java.io.BufferedReader;

/**
 * @author rshen
 * 
 */
public class War extends Challenge {
	private static int[][] graph;

	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader("test/war_test");
		int nPeople = Integer.parseInt(reader.readLine());

		graph = new int[nPeople][nPeople];
		for (int i = 0; i < nPeople; i++) {
			graph[i][i] = 1;
		}

		String line = null;
		while ((line = reader.readLine()) != null && !line.equals("0 0 0")) {
			String[] commands = line.split(" ");
			int command = Integer.parseInt(commands[0]);
			int from = Integer.parseInt(commands[1]);
			int to = Integer.parseInt(commands[2]);

			switch (command) {
			case 1:
				setFriends(from, to);
				break;
			case 2:
				setEnemies(from, to);
				break;
			case 3:
				areFriends(from, to);
				break;
			case 4:
				areEnemies(from, to);
			}
		}
	}

	private static void setFriends(int from, int to) {
		if (graph[from][to] == 0) {
			graph[from][to] = 1;
			graph[to][from] = 1;

			// Set the transitive closures
			for (int i = 0; i < graph.length; i++) {
				// A friend's friend is also a friend
				if (graph[from][i] == 1 && graph[to][i] == 0) {
					setFriends(to, i);
				}
				if (graph[to][i] == 1 && graph[from][i] == 0) {
					setFriends(from, i);
				}
			}
		} else if (graph[from][to] == -1) {
			System.out.println("-1");
		}
	}

	private static void setEnemies(int from, int to) {
		if (graph[from][to] == 0) {
			graph[from][to] = -1;
			graph[to][from] = -1;

			// Set the transitive closure
			for (int i = 0; i < graph.length; i++) {
				// An enemy's friend is an enemy
				if (graph[from][i] == 1 && graph[to][i] == 0) {
					setEnemies(to, i);
				}
				if (graph[to][i] == 1 && graph[from][i] == 0) {
					setEnemies(from, i);
				}
				// An enemy's enemy is a friend
				if (graph[from][i] == -1 && graph[to][i] == 0) {
					setFriends(to, i);
				}
				if (graph[to][i] == -1 && graph[from][i] == 0) {
					setFriends(from, i);
				}
			}
		} else if (graph[from][to] == 1) {
			System.out.println("-1");
		}
	}

	private static void areFriends(int from, int to) {
		if (graph[from][to] == 1) {
			System.out.println("1");
		} else {
			System.out.println("0");
		}
	}

	private static void areEnemies(int from, int to) {
		if (graph[from][to] == -1) {
			System.out.println("1");
		} else {
			System.out.println("0");
		}
	}
}
