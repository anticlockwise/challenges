/**
 * 
 */
package org.jaden.challenges;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * @author rshen
 * 
 */
public class BiggerSmarter extends Challenge {
	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader("test/biggersmarter_test");
		String line = null;

		List<Elephant> elephants = new ArrayList<Elephant>();
		int index = 0;
		while ((line = reader.readLine()) != null) {
			String[] e = line.split(" ");
			elephants.add(new Elephant(index++, Integer.parseInt(e[0]), Integer
					.parseInt(e[1])));
		}

		Collections.sort(elephants);
		int[] length = new int[index];
		int[] parent = new int[index];
		length[0] = 1;
		for (int i = 0; i < index; i++) {
			parent[i] = -1;
		}

		int maxLen = 0;
		int maxIndex = -1;
		for (int i = 1; i < index; i++) {
			Elephant el = elephants.get(i);
			int max = 1;
			int maxI = -1;
			for (int j = i - 1; j >= 0; j--) {
				Elephant ej = elephants.get(j);
				if (length[j] + 1 > max && el.iq < ej.iq
						&& el.weight > ej.weight) {
					max = length[j] + 1;
					maxI = j;
				}
			}
			length[i] = max;
			parent[i] = maxI;

			if (max > maxLen) {
				maxLen = max;
				maxIndex = i;
			}
		}

		System.out.println(maxLen);
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(elephants.get(maxIndex).index + 1);
		int p = parent[maxIndex];
		while (p != -1) {
			Elephant e = elephants.get(p);
			stack.push(e.index + 1);
			p = parent[p];
		}

		while (!stack.isEmpty()) {
			System.out.println(stack.pop());
		}
	}

	private static class Elephant implements Comparable<Elephant> {
		private int index;

		private int weight;

		private int iq;

		public Elephant(int index, int weight, int iq) {
			this.index = index;
			this.weight = weight;
			this.iq = iq;
		}

		public int compareTo(Elephant e) {
			if (this.weight > e.weight) {
				return 1;
			} else if (this.weight < e.weight) {
				return -1;
			}
			return 0;
		}

		public String toString() {
			return index + ": " + weight + ", " + iq;
		}
	}
}
