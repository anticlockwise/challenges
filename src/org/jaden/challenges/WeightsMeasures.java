/**
 * 
 */
package org.jaden.challenges;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author rshen
 * 
 */
public class WeightsMeasures extends Challenge {
	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader("test/wm_test");
		String line = null;

		List<Turtle> turtles = new ArrayList<Turtle>();
		while ((line = reader.readLine()) != null) {
			String[] c = line.split(" ");
			Turtle t = new Turtle(Integer.parseInt(c[0]),
					Integer.parseInt(c[1]));
			turtles.add(t);
		}
		Collections.sort(turtles);

		int size = turtles.size();
		int max = 0;
		int[] len = new int[size];
		int[] weights = new int[size];
		len[0] = 1;
		weights[0] = turtles.get(0).capacity;
		for (int i = 1; i < size; i++) {
			Turtle t = turtles.get(i);

			int maxLen = 0;
			int maxI = -1;
			for (int j = i - 1; j >= 0; j--) {
				if (t.weight <= weights[j] && len[j] + 1 > maxLen) {
					maxLen = len[j] + 1;
					maxI = j;
				}
			}

			len[i] = maxLen;
			if (maxLen > max) {
				max = maxLen;
			}
			if (maxI == -1) {
				weights[i] = t.capacity;
			} else {
				weights[i] = weights[maxI] - t.weight > t.capacity ? weights[maxI]
						- t.weight
						: t.capacity;
			}
		}

		System.out.println(max);
	}

	private static class Turtle implements Comparable<Turtle> {
		private int weight;

		private int capacity;

		public Turtle(int weight, int capacity) {
			this.weight = weight;
			this.capacity = capacity - weight;
		}

		public int compareTo(Turtle t) {
			if (this.capacity > t.capacity) {
				return -1;
			} else if (this.capacity < t.capacity) {
				return 1;
			} else {
				return 0;
			}
		}
	}
}
