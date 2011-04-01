/**
 * 
 */
package org.jaden.challenges;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @author rshen
 * 
 */
public class ProblemSetter extends Challenge {
	private static Map<Integer, List<FlowEdge>> graph;

	private static int[] parents;

	private static boolean[] visited;

	private static int nVertices;

	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader("test/problemsetter_test");
		String line = null;

		while ((line = reader.readLine()) != null && !line.equals("0 0")) {
			String[] c = line.split(" ");
			int nCategories = Integer.parseInt(c[0]);
			int nProblems = Integer.parseInt(c[1]);
			int sumCategories = 0;

			nVertices = nCategories + nProblems + 2;
			parents = new int[nVertices];
			visited = new boolean[nVertices];
			graph = new HashMap<Integer, List<FlowEdge>>();
			for (int i = 0; i < nVertices; i++) {
				graph.put(i, new ArrayList<FlowEdge>());
				parents[i] = -1;
				visited[i] = false;
			}

			line = reader.readLine();
			c = line.split(" ");
			for (int i = nProblems + 1; i < nVertices - 1; i++) {
				int capacity = Integer.parseInt(c[i - nProblems - 1]);
				sumCategories += capacity;
				graph.get(i).add(
						new FlowEdge(nVertices - 1, capacity, capacity));
				graph.get(nVertices - 1).add(new FlowEdge(i, capacity, 0));
			}

			for (int i = 1; i <= nProblems; i++) {
				graph.get(0).add(new FlowEdge(i, 1, 1));
				graph.get(i).add(new FlowEdge(0, 1, 0));

				line = reader.readLine();
				c = line.split(" ");
				int n = Integer.parseInt(c[0]);
				for (int j = 1; j <= n; j++) {
					int category = Integer.parseInt(c[j]) + nProblems;
					graph.get(i).add(new FlowEdge(category, 1, 1));
					graph.get(category).add(new FlowEdge(i, 1, 0));
				}
			}

			if (nProblems < sumCategories) {
				System.out.println("0");
				continue;
			}

			int maxFlow = 0;
			int source = 0;
			int sink = nVertices - 1;
			bfs(graph, source);
			int volume = pathVolume(graph, source, sink, parents);

			while (volume > 0) {
				maxFlow += volume;
				augmentPath(graph, source, sink, parents, volume);
				initializeSearch();
				bfs(graph, source);
				volume = pathVolume(graph, source, sink, parents);
			}

			if (maxFlow >= sumCategories) {
				System.out.println("1");
				for (int i = nProblems + 1; i < nVertices - 1; i++) {
					List<FlowEdge> edges = graph.get(i);
					for (FlowEdge e : edges) {
						if (e.residual == 1) {
							System.out.format("%d ", e.next);
						}
					}
					System.out.print("\n");
				}
			} else {
				System.out.println("0");
			}
		}
	}

	private static void augmentPath(Map<Integer, List<FlowEdge>> graph,
			int start, int end, int[] parents, int volume) {
		if (start == end) {
			return;
		}

		FlowEdge e = findEdge(graph, parents[end], end);
		e.flow += volume;
		e.residual -= volume;

		e = findEdge(graph, end, parents[end]);
		e.residual += volume;

		augmentPath(graph, start, parents[end], parents, volume);
	}

	private static int pathVolume(Map<Integer, List<FlowEdge>> graph,
			int start, int end, int[] parents) {
		if (parents[end] == -1)
			return 0;

		FlowEdge e = findEdge(graph, parents[end], end);

		if (start == parents[end]) {
			return e.residual;
		} else {
			return Math.min(pathVolume(graph, start, parents[end], parents),
					e.residual);
		}
	}

	private static FlowEdge findEdge(Map<Integer, List<FlowEdge>> graph, int x,
			int y) {
		List<FlowEdge> edges = graph.get(x);
		for (FlowEdge e : edges) {
			if (e.next == y) {
				return e;
			}
		}
		return null;
	}

	private static void bfs(Map<Integer, List<FlowEdge>> graph, int source) {
		Queue<Integer> q = new LinkedList<Integer>();
		q.offer(source);
		visited[source] = true;

		while (!q.isEmpty()) {
			int v = q.poll();
			List<FlowEdge> edges = graph.get(v);
			for (FlowEdge e : edges) {
				if (validEdge(e)) {
					if (!visited[e.next]) {
						q.offer(e.next);
						visited[e.next] = true;
						parents[e.next] = v;
					}
				}
			}
		}
	}

	private static boolean validEdge(FlowEdge e) {
		return e.residual > 0;
	}

	private static void initializeSearch() {
		for (int i = 0; i < nVertices; i++) {
			parents[i] = -1;
			visited[i] = false;
		}
	}
}
