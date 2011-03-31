/**
 * 
 */
package org.jaden.challenges;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author rshen
 * 
 */
public class Necklace extends Challenge {
	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader("test/necklace_test");
		int nCases = Integer.parseInt(reader.readLine());
		int caseNum = 1;

		for (int i = 0; i < nCases; i++) {
			int beads = Integer.parseInt(reader.readLine());
			int nVertices = 0;
			List<Integer>[] graph = new List[51];
			for (int j = 0; j < beads; j++) {
				String[] colors = reader.readLine().trim().split(" ");
				int color1 = Integer.parseInt(colors[0]);
				int color2 = Integer.parseInt(colors[1]);
				if (graph[color1] == null) {
					graph[color1] = new ArrayList<Integer>();
				}
				if (graph[color2] == null) {
					graph[color2] = new ArrayList<Integer>();
				}
				graph[color1].add(color2);
				graph[color2].add(color1);
				if (color1 > nVertices) {
					nVertices = color1;
				} else if (color2 > nVertices) {
					nVertices = color2;
				}
			}

			Stack<Integer> stack = new Stack<Integer>();
			int start = 1;
			List<String> necklace = new ArrayList<String>();
			while (tour(graph, start, stack, nVertices) == start
					&& !stack.isEmpty()) {
				int v = stack.pop();
				necklace.add(start + " " + v);
				start = v;
			}

			System.out.format("Case #%d\n", caseNum++);
			if (necklace.size() == beads) {
				for (String b : necklace) {
					System.out.println(b);
				}
				System.out.println();
			} else {
				System.out.println("some beads may be lost\n");
			}
		}
	}

	private static int tour(List<Integer>[] graph, int v, Stack<Integer> stack,
			int nVertices) {
		while (true) {
			List<Integer> edges = graph[v];
			if (edges.isEmpty())
				break;
			stack.push(v);
			int w = edges.get(0);
			edges.remove(0);
			boolean r = graph[w].remove((Object) v);
			v = w;
			// printGraph(graph, nVertices);
		}
		return v;
	}

	private static void printGraph(List<Integer>[] graph, int nVertices) {
		for (int i = 1; i <= nVertices; i++) {
			System.out.println(i + ": " + graph[i]);
		}
		System.out.println("============");
	}
}
