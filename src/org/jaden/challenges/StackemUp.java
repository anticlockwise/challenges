/**
 * 
 */
package org.jaden.challenges;

import java.io.BufferedReader;
import java.util.Arrays;

/**
 * @author rshen
 * 
 */
public class StackemUp extends Challenge {
	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader("test/stackem_test");
		int numCases = Integer.parseInt(reader.readLine());
		reader.readLine();

		for (int i = 0; i < numCases; i++) {
			int numDeals = Integer.parseInt(reader.readLine());
			int[][] deals = new int[numDeals][52];

			for (int j = 0; j < numDeals; j++) {
				String firstLine = reader.readLine().trim();
				String secondLine = reader.readLine().trim();
				String[] numbers = firstLine.split(" ");
				int k = 0;
				for (String num : numbers) {
					deals[j][k++] = Integer.parseInt(num);
				}
				numbers = secondLine.split(" ");
				for (String num : numbers) {
					deals[j][k++] = Integer.parseInt(num);
				}
			}

			String line = null;
			int[] origDeck = new int[52];
			for (int suit = 0; suit < 4; suit++) {
				for (int v = 0; v < 12; v++) {
					origDeck[suit * 13 + v] = suit * 13 + v + 2;
				}
				origDeck[suit * 13 + 12] = suit * 13 + 1;
			}

			while ((line = reader.readLine()) != null
					&& !line.trim().equals("")) {
				int deal = Integer.parseInt(line);
				int[] tmp = new int[52];
				int[] shuffle = deals[deal - 1];
				for (int j = 0; j < shuffle.length; j++) {
					tmp[j] = origDeck[shuffle[j] - 1];
				}
				System.arraycopy(tmp, 0, origDeck, 0, 52);
			}

			for (int card : origDeck) {
				System.out.println(getCard(card));
			}
		}
	}

	private static String getCard(int val) {
		int suit = (val - 1) / 13;
		int value = (val - 1) % 13;
		String s = null;
		String v = null;

		switch (suit) {
		case 0:
			s = "Clubs";
			break;
		case 1:
			s = "Diamonds";
			break;
		case 2:
			s = "Hearts";
			break;
		case 3:
			s = "Spades";
			break;
		}

		switch (value) {
		case 0:
			v = "Ace";
			break;
		case 10:
			v = "Jack";
			break;
		case 11:
			v = "Queen";
			break;
		case 12:
			v = "King";
			break;
		default:
			v = Integer.toString(value + 1);
		}

		return (v + " of " + s);
	}
}
