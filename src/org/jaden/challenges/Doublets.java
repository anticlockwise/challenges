package org.jaden.challenges;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * <h3>Doublets</h3>
 * <p>
 * PC/UVa IDs: 110307/10150, Popularity: C, Success rate: average Level: 3
 * </p>
 * 
 * @author rshen
 * 
 */
public class Doublets extends Challenge {
	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader("test/doublets_test");
		String line = null;

		List<char[]> dict = new ArrayList<char[]>();
		Map<String, List<String>> graph = new HashMap<String, List<String>>();

		while ((line = reader.readLine()) != null && !line.trim().equals("")) {
			char[] s = line.trim().toCharArray();
			dict.add(s);
			graph.put(line.trim(), new ArrayList<String>());
		}

		int size = dict.size();
		for (int i = 0; i < size; i++) {
			char[] s = dict.get(i);
			for (int j = i + 1; j < size; j++) {
				char[] d = dict.get(j);
				if (connect(s, d)) {
					String sStr = new String(s);
					String dStr = new String(d);
					graph.get(sStr).add(dStr);
					graph.get(dStr).add(sStr);
				}
			}
		}

		while ((line = reader.readLine()) != null) {
			String[] c = line.split(" ");
			String[] result = bfs(graph, c[0], c[1]);
			if (result == null) {
				System.out.println("No solution\n");
			} else {
				for (int i = result.length - 1; i >= 0; i--) {
					System.out.println(result[i]);
				}
				System.out.println();
			}
		}
	}

	private static String[] bfs(Map<String, List<String>> graph, String start,
			String end) {
		Map<String, String> parents = new HashMap<String, String>();
		Map<String, Boolean> visited = new HashMap<String, Boolean>();
		Queue<String> queue = new LinkedList<String>();
		queue.offer(start);

		boolean found = false;
		while (!queue.isEmpty()) {
			String from = queue.poll();
			visited.put(from, true);
			List<String> l = graph.get(from);
			for (String to : l) {
				if (!visited.containsKey(to)) {
					parents.put(to, from);
					if (to.equals(end)) {
						found = true;
						break;
					}
					queue.offer(to);
				}
			}

			if (found)
				break;
		}

		if (!found) {
			return null;
		} else {
			List<String> result = new ArrayList<String>();
			String e = end;
			while (!e.equals(start)) {
				result.add(e);
				e = parents.get(e);
			}
			result.add(new String(e));
			return result.toArray(new String[result.size()]);
		}
	}

	private static void printGraph(Map<String, List<String>> graph) {
		Set<String> keys = graph.keySet();
		for (String key : keys) {
			System.out.print(new String(key) + ": ");
			List<String> l = graph.get(key);
			for (String v : l) {
				System.out.print(v + " ");
			}
			System.out.print("\n");
		}
	}

	private static boolean connect(char[] s, char[] d) {
		if (s.length != d.length)
			return false;

		int diff = 0;
		for (int i = 0; i < s.length; i++) {
			if (s[i] != d[i])
				diff++;

			if (diff > 1)
				return false;
		}

		return true;
	}
}
