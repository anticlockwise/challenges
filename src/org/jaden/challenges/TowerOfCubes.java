/**
 * 
 */
package org.jaden.challenges;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * @author rshen
 * 
 */
public class TowerOfCubes extends Challenge {
	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader("test/toc_test");
		String line = null;

		while ((line = reader.readLine()) != null && !line.trim().equals("0")) {
			int nCubes = Integer.parseInt(line.trim());

			Map<Integer, List<Integer>> graph = new HashMap<Integer, List<Integer>>();
			int[][] cubes = new int[nCubes][6];
			for (int i = 0; i < nCubes; i++) {
				line = reader.readLine().trim();
				String[] colors = line.split(" ");
				for (int j = 0; j < colors.length; j++) {
					cubes[i][j] = Integer.parseInt(colors[j]);
				}
			}

			for (int i = 0; i < nCubes; i++) {
				int[] cubei = cubes[i];
				for (int k = 0; k < cubei.length; k++) {
					List<Integer> links = new ArrayList<Integer>();
					graph.put(i * 6 + k, links);
				}
				for (int j = i + 1; j < nCubes; j++) {
					int[] cubej = cubes[j];

					for (int m = 0; m < cubei.length; m++) {
						List<Integer> links = graph.get(i * 6 + m);
						for (int n = 0; n < cubej.length; n++) {
							if (cubei[m] == cubej[n]) {
								links.add(j * 6 + n);
							}
						}
					}
				}
			}

			int maxDepth = 0;
			int maxVertice = -1;
			int[] parent = new int[nCubes * 6];
			for (int i = 0; i < nCubes * 6; i++) {
				int[] d = dfs(graph, i, parent);
				if (d[0] > maxDepth) {
					maxDepth = d[0];
					maxVertice = d[1];
				}
			}

			int p = parent[maxVertice];
			Stack<Integer> stack = new Stack<Integer>();
			while (p != 0) {
				stack.push(p);
				p = parent[p];
			}
			while (!stack.isEmpty()) {
				System.out.println(getName(stack.pop()));
			}
			System.out.println(getName(maxVertice));
			System.out.println(maxDepth);
		}
	}

	private static String getName(int n) {
		int q = n / 6 + 1;
		int r = n % 6;
		String rn = null;
		switch (r) {
		case 0:
			rn = "front";
			break;
		case 1:
			rn = "back";
			break;
		case 2:
			rn = "left";
			break;
		case 3:
			rn = "right";
			break;
		case 4:
			rn = "top";
			break;
		case 5:
			rn = "bottom";
			break;
		}
		return q + " " + rn;
	}

	private static int[] dfs(Map<Integer, List<Integer>> graph, int v,
			int[] parent) {
		List<Integer> links = graph.get(v);
		if (links.isEmpty()) {
			return new int[] { 1, v };
		}

		int maxDepth = 0;
		int maxVertice = -1;
		for (Integer t : links) {
			int[] d = dfs(graph, t, parent);
			if (d[0] + 1 > maxDepth) {
				parent[t] = v;
				maxDepth = d[0] + 1;
				maxVertice = d[1];
			}
		}
		return new int[] { maxDepth, maxVertice };
	}
}
