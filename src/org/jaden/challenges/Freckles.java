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
public class Freckles extends Challenge {
	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader("test/freckles_test");
		int nCases = Integer.parseInt(reader.readLine().trim());

		for (int i = 0; i < nCases; i++) {
			reader.readLine();
			int nVertices = Integer.parseInt(reader.readLine().trim());
			List<double[]> vertices = new ArrayList<double[]>();
			Map<Integer, List<Edge>> graph = new HashMap<Integer, List<Edge>>();
			for (int j = 0; j < nVertices; j++) {
				String[] v = reader.readLine().trim().split(" ");
				double[] vertice = { Double.parseDouble(v[0]),
						Double.parseDouble(v[1]) };
				vertices.add(vertice);
				graph.put(j, new ArrayList<Edge>());
			}

			for (int j = 0; j < nVertices; j++) {
				double[] from = vertices.get(j);
				for (int k = j + 1; k < nVertices; k++) {
					double[] to = vertices.get(k);
					double d = getDistance(from, to);
					graph.get(j).add(new Edge(k, d));
					graph.get(k).add(new Edge(j, d));
				}
			}

			System.out.format("%.2f", mst(graph, nVertices));
		}
	}

	private static double mst(Map<Integer, List<Edge>> graph, int nVertices) {
		boolean[] inTree = new boolean[nVertices];
		double[] distances = new double[nVertices];
		int[] parent = new int[nVertices];
		int start = 0;
		for (int i = 0; i < nVertices; i++) {
			distances[i] = Double.MAX_VALUE;
			parent[i] = -1;
		}

		distances[start] = 0.0;
		int v = start;
		double minDist = 0.0;
		while (!inTree[v]) {
			inTree[v] = true;
			List<Edge> edges = graph.get(v);
			for (Edge edge : edges) {
				int w = edge.index;
				double weight = edge.distance;
				if (distances[w] > weight && !inTree[w]) {
					distances[w] = weight;
					parent[w] = v;
				}
			}

			v = 0;
			double dist = Double.MAX_VALUE;
			for (int i = 1; i < nVertices; i++) {
				if (!inTree[i] && dist > distances[i]) {
					dist = distances[i];
					v = i;
				}
			}

			if (dist != Double.MAX_VALUE) {
				minDist += dist;
			}
		}

		return minDist;
	}

	private static double getDistance(double[] from, double[] to) {
		return Math.sqrt((to[0] - from[0]) * (to[0] - from[0])
				+ (to[1] - from[1]) * (to[1] - from[1]));
	}

	private static class Edge {
		private double distance;

		private int index;

		public Edge(int index, double distance) {
			this.index = index;
			this.distance = distance;
		}
	}
}
