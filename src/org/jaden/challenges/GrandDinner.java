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
 * <h3>The Grand Dinner</h3>
 * <p>
 * PC/UVa IDs: 111007/10249, Popularity: C, Success rate: high Level: 4
 * </p>
 * <p>
 * Maximum Flow problem: graph contains a source vertex, a vertex for each team,
 * a vertex for each table and a sink vertex.
 * <ol>
 * <li>Build an edge between the source vertex and every team vertex with
 * capacity equal to the number of people in the team.</li>
 * <li>Build an edge between each team and each table with capacity equal to 1 -
 * no member from the same team can sit at the same table.</li>
 * <li>Build an edge between each table and the sink vertex with capacity equal
 * to the size of the table.</li>
 * </ol>
 * </p>
 * 
 * @author rshen
 * 
 */
public class GrandDinner extends Challenge {
	private static Map<Integer, List<FlowEdge>> graph;

	private static int[] parents;

	private static boolean[] visited;

	private static int nVertices;

	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader("test/granddinner_test");
		String line = null;

		while ((line = reader.readLine()) != null && !line.equals("0 0")) {
			String[] c = line.split(" ");
			int nTeams = Integer.parseInt(c[0]);
			int nTables = Integer.parseInt(c[1]);
			int sumTeams = 0;
			int sumTables = 0;
			int flag = 1;

			nVertices = nTeams + nTables + 2;
			parents = new int[nVertices];
			visited = new boolean[nVertices];
			graph = new HashMap<Integer, List<FlowEdge>>();
			for (int i = 0; i < nTeams + nTables + 2; i++) {
				graph.put(i, new ArrayList<FlowEdge>());
				parents[i] = -1;
				visited[i] = false;
			}

			c = reader.readLine().trim().split(" ");
			for (int i = 0; i < nTeams; i++) {
				int team = Integer.parseInt(c[i]);
				sumTeams += team;
				if (team > nTables)
					flag = 0;

				graph.get(0).add(new FlowEdge(i + 1, team, team));
				// Add residual edge
				graph.get(i + 1).add(new FlowEdge(0, team, 0));
			}

			c = reader.readLine().trim().split(" ");
			for (int i = 0; i < nTables; i++) {
				int table = Integer.parseInt(c[i]);
				sumTables += table;
				int tn = i + nTeams + 1;
				for (int j = 1; j <= nTeams; j++) {
					graph.get(j).add(new FlowEdge(tn, 1, 1));
					graph.get(tn).add(new FlowEdge(j, 1, 0));
				}
				graph.get(tn).add(new FlowEdge(nTeams + nTables + 1, table, table));
				graph.get(nTeams + nTables + 1).add(new FlowEdge(tn, table, 0));
			}

			if (flag == 0 || sumTeams > sumTables) {
				System.out.println("0");
				continue;
			}

			int maxFlow = 0;
			int source = 0;
			int sink = nTeams + nTables + 1;
			bfs(graph, source);
			int volume = pathVolume(graph, source, sink, parents);

			while (volume > 0) {
				maxFlow += volume;
				augmentPath(graph, source, sink, parents, volume);
				initializeSearch();
				bfs(graph, source);
				volume = pathVolume(graph, source, sink, parents);
			}

			if (maxFlow >= sumTeams) {
				System.out.println("1");
				for (int i = 1; i <= nTeams; i++) {
					List<FlowEdge> edges = graph.get(i);
					for (FlowEdge e : edges) {
						if (e.residual == 0) {
							System.out.format("%d ", e.next - nTeams);
						}
					}
					System.out.print("\n");
				}
			} else {
				System.out.println("0");
			}
		}
	}

	private static void augmentPath(Map<Integer, List<FlowEdge>> graph, int start,
			int end, int[] parents, int volume) {
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

	private static int pathVolume(Map<Integer, List<FlowEdge>> graph, int start,
			int end, int[] parents) {
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

	private static FlowEdge findEdge(Map<Integer, List<FlowEdge>> graph, int x, int y) {
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
