/**
 * 
 */
package org.jaden.challenges;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rshen
 * 
 */
public class EditStepLadder extends Challenge {
	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader("test/editstepladder_test");
		String line = null;

		List<String> words = new ArrayList<String>();
		while ((line = reader.readLine()) != null) {
			words.add(line.trim());
		}

		Map<Integer, List<Integer>> graph = new HashMap<Integer, List<Integer>>();
		int size = words.size();
		for (int i = 0; i < size; i++) {
			String w = words.get(i);
			char[] wChar = w.toCharArray();
			List<Integer> links = new ArrayList<Integer>();
			graph.put(i, links);
			for (int j = i + 1; j < size; j++) {
				String t = words.get(j);
				char[] tChar = t.toCharArray();
				if (editStep(wChar, tChar)) {
					links.add(j);
				}
			}
		}

		int maxD = 0;
		boolean[] visited = new boolean[size];
		for (int i = 0; i < size; i++) {
			if (!visited[i]) {
				int d = dfs(graph, i, visited) + 1;
				if (d > maxD)
					maxD = d;
			}
		}
		System.out.println(maxD);
	}

	private static int dfs(Map<Integer, List<Integer>> graph, int v,
			boolean[] visited) {
		visited[v] = true;
		List<Integer> links = graph.get(v);
		if (links.isEmpty()) {
			return 1;
		}

		int maxDepth = 0;
		for (Integer t : links) {
			if (!visited[t]) {
				int depth = dfs(graph, t, visited) + 1;
				if (depth > maxDepth)
					maxDepth = depth;
			}
		}
		return maxDepth;
	}

	private static boolean editStep(char[] w, char[] t) {
		int len = Math.abs(w.length - t.length);
		if (len > 1) {
			return false;
		} else if (len == 1) {
			int sLen = w.length > t.length ? t.length : w.length;
			for (int i = 0; i < sLen; i++) {
				if (w[i] != t[i])
					return false;
			}
			return true;
		} else {
			int steps = 0;
			for (int i = 0; i < w.length; i++) {
				if (w[i] != t[i])
					steps++;
			}
			return steps > 1;
		}
	}
}
