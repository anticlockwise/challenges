/**
 * 
 */
package org.jaden.challenges;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

/**
 * <h3>Bicoloring</h3>
 * <p>
 * PC/UVa IDs: 110901/10004, Popularity: A, Success rate: high Level: 1
 * </p>
 * 
 * @author rshen
 * 
 */
public class Bicoloring extends Challenge {
	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader("test/bicoloring_test");
		String line = null;

		while ((line = reader.readLine()) != null && !line.trim().equals("0")) {
			int nVertices = Integer.parseInt(line.trim());
			int nEdges = Integer.parseInt(reader.readLine().trim());

			List<Integer>[] graph = new List[nVertices];
			for (int i = 0; i < nVertices; i++) {
				graph[i] = new ArrayList<Integer>();
			}

			for (int i = 0; i < nEdges; i++) {
				String[] nodes = reader.readLine().split(" ");
				int from = Integer.parseInt(nodes[0]);
				int to = Integer.parseInt(nodes[1]);
				graph[from].add(to);
				graph[to].add(from);
			}

			int[] colors = new int[nVertices];
			boolean[] visited = new boolean[nVertices];
			boolean result = bicolor(graph, 0, visited, colors, 0);

			if (result) {
				System.out.println("BICOLORABLE.");
			} else {
				System.out.println("NOT BICOLORABLE.");
			}
		}
	}

	private static boolean bicolor(List<Integer>[] graph, int v,
			boolean[] visited, int[] colors, int color) {
		visited[v] = true;
		colors[v] = color;
		List<Integer> nl = graph[v];
		boolean bicolorable = true;
		for (int t : nl) {
			if (!visited[t]) {
				bicolorable = bicolorable
						&& bicolor(graph, t, visited, colors, (color + 1) % 2);
			} else if (color == colors[t]) {
				bicolorable = false;
			}
		}
		return bicolorable;
	}
}
