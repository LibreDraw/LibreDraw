/*
	This file is part of LibreDraw.

    LibreDraw is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    LibreDraw is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with LibreDraw.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.libredraw.parser;

public class Lexer {
	private Tokenizer tokens;
	private int tokenChar;
	private String tokenString;

	public static final int STRING_TOKEN = -1;
	public static final int EOLN_TOKEN = -2;

	/**
	 * Create a lexer.
	 * 
	 * @param s
	 *            Lex this string.
	 */
	public Lexer(String s) {
		/**
		 * We use a StringTokenizer to tokenize the string. Our delimiters are
		 * the operators, parens, and white space. By making the third parameter
		 * true we instruct the StringTokenizer to return those delimiters as
		 * tokens.
		 */

		tokens = new Tokenizer(s, new char[] { ' ', '\t', '\n', '\r', ':', '[',
				']', '(', ')', '#', '~', '+', '-', ',' }, true);

		/**
		 * Start by advancing to the first token. Note that this may get an
		 * error, which would set our errorMessage instead of setting tokenChar.
		 */

		advance();
	}

	/**
	 * Advance to the next token. We don't return anything; the caller must use
	 * nextToken() to see what that token is.
	 */
	public void advance() {

		// White space is returned as a token by our
		// StringTokenizer, but we will loop until something
		// other than white space has been found.

		while (true) {
			// If we're at the end, make it an EOLN_TOKEN.

			if (!tokens.hasMoreTokens()) {
				tokenChar = EOLN_TOKEN;
				return;
			}

			// Get a token--if it looks like a number,
			// make it a NUMBER_TOKEN.

			String s = tokens.nextToken();
			char[] temp = s.toCharArray();
			char c1 = temp[0];
			System.out.println(c1);
			if (s.length() > 1) {
				tokenString = s;
				tokenChar = STRING_TOKEN;
				return;
			}
			// Any other single character that is not
			// white space is a token.

			else if (s.length() == 1 && !isWhiteSpace(c1)) {
				tokenString = s;
				tokenChar = c1;
				return;
			}
		}
	}

	/**
	 * Get token as string.
	 * 
	 * @return A token.
	 */
	public String getString() {
		return tokenString;
	}

	/**
	 * Returns the next token as char
	 * 
	 * @return A token.
	 */
	public int nextToken() {
		return tokenChar;
	}

	/**
	 * Checks if a char is white space
	 * 
	 * @param Checkme
	 *            Char to be checked
	 * @return True if char is whitespace
	 */
	private boolean isWhiteSpace(char Checkme) {
		char[] whitespace = new char[] { ' ', '\t', '\n', '\r' };
		for (char c : whitespace)
			if (c == Checkme)
				return true;
		return false;

	}
}