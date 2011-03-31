/**
 * 
 */
package org.jaden.challenges;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author rshen
 * 
 */
public class TouristGuide extends Challenge {
	private static boolean[] visited;

	private static int time;

	private static int[] entryTime;

	private static int[] exitTime;

	private static boolean[] processed;

	private static int[] reachableAncestor;

	private static int[] treeOutDegree;

	private static int[] parent;

	private static Map<String, Integer> map;

	private static Map<Integer, List<Integer>> graph;

	private static HashSet<Integer> cameras;

	private static String[] cities;

	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader("test/touristguide_test");
		String line = null;

		int index = 1;
		while ((line = reader.readLine()) != null && !line.equals("0")) {
			map = new HashMap<String, Integer>();
			graph = new HashMap<Integer, List<Integer>>();
			cameras = new HashSet<Integer>();

			int nCities = Integer.parseInt(line.trim());
			time = 0;
			visited = new boolean[nCities];
			entryTime = new int[nCities];
			exitTime = new int[nCities];
			parent = new int[nCities];
			processed = new boolean[nCities];
			reachableAncestor = new int[nCities];
			treeOutDegree = new int[nCities];
			cities = new String[nCities];
			for (int i = 0; i < nCities; i++) {
				line = reader.readLine().trim();
				map.put(line, i);
				parent[i] = -1;
				cities[i] = line;
				graph.put(i, new ArrayList<Integer>());
			}

			int nRoads = Integer.parseInt(reader.readLine().trim());
			for (int i = 0; i < nRoads; i++) {
				line = reader.readLine();
				String[] cities = line.split(" ");
				int from = map.get(cities[0]);
				int to = map.get(cities[1]);
				graph.get(from).add(to);
				graph.get(to).add(from);
			}

			dfs(graph, 0);
			int nc = cameras.size();
			System.out
					.format("City map #%d: %d camera(s) found\n", index++, nc);
			for (Integer c : cameras) {
				System.out.println(cities[c]);
			}
			System.out.println();
		}
	}

	private static void dfs(Map<Integer, List<Integer>> graph, int v) {
		visited[v] = true;
		time = time + 1;
		entryTime[v] = time;

		reachableAncestor[v] = v;

		List<Integer> edges = graph.get(v);
		for (int y : edges) {
			if (!visited[y]) {
				parent[y] = v;
				processEdge(v, y);
				dfs(graph, y);
			} else if (!processed[y]) {
				processEdge(v, y);
			}
		}

		processVertexLate(v);

		time = time + 1;
		exitTime[v] = time;
		processed[v] = true;
	}

	private static void processVertexLate(int v) {
		if (parent[v] < 0) {
			if (treeOutDegree[v] > 1) {
				cameras.add(v);
			}
			return;
		}

		boolean root = (parent[parent[v]] < 0);
		if ((reachableAncestor[v] == parent[v]) && (!root)) {
			cameras.add(parent[v]);
		}

		if (reachableAncestor[v] == v && (!root)) {
			cameras.add(parent[v]);

			if (treeOutDegree[v] > 0) {
				cameras.add(v);
			}
		}

		int timeV = entryTime[reachableAncestor[v]];
		int timeParent = entryTime[reachableAncestor[parent[v]]];

		if (timeV < timeParent) {
			reachableAncestor[parent[v]] = reachableAncestor[v];
		}
	}

	private static void processEdge(int x, int y) {
		int c = edgeClassification(x, y);

		if (c == 1) { // If it's a tree edge
			treeOutDegree[x] = treeOutDegree[x] + 1;
		}

		if (c == 2 && parent[x] != y) {
			if (entryTime[y] < entryTime[reachableAncestor[x]]) {
				reachableAncestor[x] = y;
			}
		}
	}

	private static int edgeClassification(int x, int y) {
		if (parent[y] == x)
			return 1; // Tree Edge
		if (visited[y] && !processed[y])
			return 2; // Back Edge
		if (processed[y] && (entryTime[y] > entryTime[x]))
			return 3; // Forward Edge
		if (processed[y] && (entryTime[y] < entryTime[x]))
			return 4; // Cross Edge
		return -1;
	}
}
