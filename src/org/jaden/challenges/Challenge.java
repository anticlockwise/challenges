package org.jaden.challenges;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Base class for all challenges
 * 
 * @author rshen
 */
public abstract class Challenge {

	protected static BufferedReader getReader(String filename) throws Exception {
		return new BufferedReader(new FileReader(filename));
	}
}
