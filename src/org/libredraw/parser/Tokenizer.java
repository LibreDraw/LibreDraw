package org.libredraw.parser;

class Tokenizer {
	/**
	 * The list of delimiters that the constructor provides.
	 */
	private char[] delimaters;

	/**
	 * The tokens that are delimited by tokemMaker.
	 */
	private String[] tokens;

	/**
	 * Count of how many tokens are available.
	 */
	private int tokenCount;

	/**
	 * Stores what tokens have been returned.
	 */
	private int tokensUsed;

	/**
	 * Determines whether or not the delimiters are to be returned as
	 * delimiters.
	 */
	private boolean keepDelimaters;

	/**
	 * Tokenizer constructor initializes member variables and delimits the
	 * string s to tokens using in_delimates.
	 * 
	 * @param s
	 *            string to be tokenized
	 * @param in_delimaters
	 *            list of delimiters
	 * @param in_keepDelimaters
	 *            whether or not to include delimiters
	 */
	public Tokenizer(String s, char[] in_delimaters, boolean in_keepDelimaters) {
		delimaters = in_delimaters;
		keepDelimaters = in_keepDelimaters;
		tokens = tokenMaker(s);
		tokenCount = tokens.length;
		tokensUsed = 0;
	}

	/**
	 * Splits the input string into tokens. 1. first explodes the string into a
	 * char array. 2. progress down the array creating strings between
	 * delimiters. 3. shorten the output array to the length of the data.
	 * 
	 * @param s
	 *            char array of input string
	 */
	private String[] tokenMaker(String s) {
		char[] tok = s.toCharArray();
		String[] result = new String[tok.length];
		int current = 0;
		for (int i = 0; i < tok.length; i++) {
			if (!isDelimater(tok[i])) {
				String cat = "";
				int n = i;
				while (n < tok.length && !isDelimater(tok[n])) {// continue to
																// add
																// characters it
																// a string
																// till a
																// delimiter is
																// found
					String temp = cat;
					cat = temp + tok[n];
					n++;
				}
				result[current++] = cat;
				i = (n - 1);
			} else if (keepDelimaters)
				result[current++] = Character.toString(tok[i]);
		}
		// fix the length of the returned array
		String[] finalResult = new String[current];
		for (int i = 0; i < (current); i++) {
			finalResult[i] = result[i];
		}
		return finalResult;
	}

	/**
	 * Get status of tokens.
	 * 
	 * @return True if there are more tokens.
	 */
	public boolean hasMoreTokens() {
		if (tokenCount == tokensUsed)
			return false;
		else
			return true;
	}

	/**
	 * Get the next token and increment the token pointer.
	 * 
	 * @return a token
	 */
	public String nextToken() {
		if (tokenCount == tokensUsed)
			return null;
		else
			return tokens[tokensUsed++];
	}

	/**
	 * Returns true if testMe is a delimiter. Determined by comparing it to
	 * every char in member delimiters.
	 * 
	 * @param testMe
	 *            the char to be compared to
	 * @return True if char is a delimiter
	 */
	private boolean isDelimater(char testMe) {
		for (char c : delimaters) {
			if (testMe == c)
				return true;
		}
		return false;
	}
}
