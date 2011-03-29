package org.jaden.challenges;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

/**
 * <h3>Crypt Kicker II</h3>
 * <p>
 * PC/UVa IDs: 110304/850, Popularity: A, Success rate: average Level: 2
 * </p>
 * 
 * @author rshen
 */
public class CryptKickerII extends Challenge {

	public static void main(String args[]) throws Exception {
		BufferedReader reader = getReader(args[0]);
		int numCases = Integer.parseInt(reader.readLine());
		reader.readLine();

		String d = "the quick brown fox jumps over the lazy dog";
		int len = d.length();
		String[] words = d.split(" ");

		for (int i = 0; i < numCases; i++) {
			String line = null;
			List<String> texts = new ArrayList<String>();
			while ((line = reader.readLine()) != null
					&& !line.trim().equals("")) {
				texts.add(line.trim());
			}

			int[] dict = new int[26];
			for (String text : texts) {
				if (text.length() == len) {
					String[] ws = text.split(" ");
					if (ws.length == words.length) {
						boolean target = true;
						for (int j = 0; j < ws.length; j++) {
							int l = ws[j].length();
							if (l == words[j].length()) {
								for (int k = 0; k < l; k++) {
									dict[ws[j].charAt(k) - 'a'] = words[j]
											.charAt(k) - 'a';
								}
							} else {
								target = false;
								break;
							}
						}

						if (target) {
							break;
						}
					}
				}
			}

			for (String text : texts) {
				char[] carr = text.toCharArray();
				for (int j = 0; j < carr.length; j++) {
					if (carr[j] != ' ') {
						carr[j] = (char) (dict[carr[j] - 'a'] + 'a');
					}
				}
				System.out.println(new String(carr));
			}
		}
	}

}
