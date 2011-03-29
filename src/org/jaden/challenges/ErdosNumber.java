package org.jaden.challenges;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * <h3>Erdos Number</h3>
 * <p>
 * <b>PC/UVa IDs</b>: 110206/10044, <b>Popularity</b>: B, <b>Success rate</b>:
 * low <b>Level</b>: 2
 * </p>
 * 
 * @author rshen
 */
public class ErdosNumber extends Challenge {

	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader(args[0]);
		int numCases = Integer.parseInt(reader.readLine());
		for (int i = 0; i < numCases; i++) {
			String[] config = reader.readLine().split(" ");
			int numPapers = Integer.parseInt(config[0]);
			int numAuthors = Integer.parseInt(config[1]);

			int[] parent = new int[numAuthors + 1];
			for (int j = 0; j < parent.length; j++) {
				parent[j] = -1;
			}

			// Store the papers for later use
			List[] papers = new List[numPapers];
			for (int j = 0; j < numPapers; j++) {
				String paper = reader.readLine();
				String[] authors = paper.split(":")[0].split(", ");

				papers[j] = new ArrayList<String>();
				for (int k = 0; k < authors.length; k += 2) {
					papers[j].add(authors[k] + ", " + authors[k + 1]);
				}
			}

			Map<Integer, List<Integer>> graph = new HashMap<Integer, List<Integer>>();
			Map<String, Integer> authorMap = new HashMap<String, Integer>();
			// Put in Erdos
			authorMap.put("Erdos, P.", 0);
			graph.put(0, new ArrayList<Integer>());

			// Map the authors to integers and construct the graph
			for (int j = 1; j <= numAuthors; j++) {
				String author = reader.readLine().trim();
				graph.put(j, new ArrayList<Integer>());
				authorMap.put(author, j);
			}

			for (int j = 0; j < papers.length; j++) {
				List<String> authors = papers[j];
				int size = authors.size();
				for (int m = 0; m < size; m++) {
					if (authorMap.containsKey(authors.get(m))) {
						int from = authorMap.get(authors.get(m));
						for (int n = m + 1; n < size; n++) {
							if (authorMap.containsKey(authors.get(n))) {
								int to = authorMap.get(authors.get(n));
								graph.get(from).add(to);
								graph.get(to).add(from);
							}
						}
					}
				}
			}

			// BFS to find the shortest path from Erdos to everyone else.
			bfs(graph, parent, authorMap.get("Erdos, P."));
			authorMap.remove("Erdos, P.");
			for (String author : authorMap.keySet()) {
				int index = authorMap.get(author);
				String erdos = Integer.toString(parent[index] + 1);
				if (erdos.equals("0")) {
					erdos = "infinity";
				}

				System.out.println(author + " " + erdos);
			}
		}
	}

	private static void bfs(Map<Integer, List<Integer>> graph, int[] parent,
			int start) {
		Queue<Integer> q = new LinkedList<Integer>();
		q.offer(start);
		boolean visited[] = new boolean[graph.size()];
		while (!q.isEmpty()) {
			int from = q.poll();
			visited[from] = true;
			List<Integer> neighbours = graph.get(from);
			for (int n : neighbours) {
				if (!visited[n]) {
					parent[n] = from;
					q.offer(n);
				}
			}
		}
	}

}
