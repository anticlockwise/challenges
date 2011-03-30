/**
 * 
 */
package org.jaden.challenges;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @author rshen
 * 
 */
public class PlayingWheels extends Challenge {
	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader("test/playwheel_test");
		int numCases = Integer.parseInt(reader.readLine());

		for (int i = 0; i < numCases; i++) {
			reader.readLine(); // Empty line
			int source = Integer.parseInt(reader.readLine().replaceAll("\\s+",
					""));
			int target = Integer.parseInt(reader.readLine().replaceAll("\\s+",
					""));

			int nRestricts = Integer.parseInt(reader.readLine());
			Map<Integer, Boolean> restricts = new HashMap<Integer, Boolean>();
			for (int j = 0; j < nRestricts; j++) {
				int r = Integer.parseInt(reader.readLine().replaceAll("\\s+",
						""));
				restricts.put(r, true);
			}

			// Construct the graph
			List<Integer>[] graph = new List[10000];
			for (int j = 0; j <= 9999; j++) {
				if (restricts.containsKey(j))
					continue;

				graph[j] = new ArrayList<Integer>();
				int p = 10;
				int[] a = toArray(j);
				addNum(graph, j, a, -1, restricts);
				addNum(graph, j, a, 1, restricts);
			}

			boolean[] visited = new boolean[10000];
			int[] parent = new int[10000];
			boolean found = false;
			Queue<Integer> q = new LinkedList<Integer>();
			q.offer(source);
			visited[source] = true;
			while (!q.isEmpty()) {
				int v = q.poll();
				if (v == target) {
					found = true;
					break;
				}

				List<Integer> l = graph[v];
				for (int t : l) {
					if (!visited[t]) {
						parent[t] = v;
						visited[t] = true;
						if (t == target) {
							found = true;
							break;
						}
						q.offer(t);
					}
				}

				if (found) {
					break;
				}
			}

			if (found) {
				int p = parent[target];
				int n = 1;
				while (p != source) {
					n++;
					p = parent[p];
				}
				System.out.println(n);
			} else {
				System.out.println("-1");
			}
		}
	}

	private static void addNum(List<Integer>[] graph, int v, int[] a, int diff,
			Map<Integer, Boolean> restricts) {
		for (int k = 0; k < 4; k++) {
			int[] m = Arrays.copyOf(a, a.length);
			m[k] = a[k] + diff;
			if (m[k] > 9) {
				m[k] = 0;
			} else if (m[k] < 0) {
				m[k] = 9;
			}
			int num = toInt(m);
			if (!restricts.containsKey(num)) {
				graph[v].add(num);
			}
		}
	}

	private static int toInt(int[] a) {
		return a[0] * 1000 + a[1] * 100 + a[2] * 10 + a[3];
	}

	private static int[] toArray(int n) {
		int[] a = new int[4];
		a[0] = (n / 1000) % 10;
		a[1] = (n / 100) % 10;
		a[2] = (n / 10) % 10;
		a[3] = n % 10;
		return a;
	}
}
