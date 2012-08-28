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

package org.libredraw.persistence.umlclassdiagram;

public class Lexer
{
	
	private class Tokenizer
	{
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
		 * Determines whether or not the delimiters are to
		 * be returned as delimiters.
		 */
		private boolean keepDelimaters;

		/**
		 * Tokenizer constructor initializes member variables
		 * and delimits the string s to tokens using in_delimates.
		 * 
		 * @param s string to be tokenized
		 * @param in_delimaters list of delimiters
		 * @param in_keepDelimaters whether or not to include delimiters
		 */
		public Tokenizer(
		String s, char[] in_delimaters, boolean in_keepDelimaters)
		{
			delimaters = in_delimaters;
			keepDelimaters = in_keepDelimaters;
			tokens = tokenMaker(s);
			tokenCount = tokens.length;
			tokensUsed = 0;
		}

		/**
		 * Splits the input string into tokens.
		 * 1. first explodes the string into a char array.
		 * 2. progress down the array creating strings between delimiters.
		 * 3. shorten the output array to the length of the data.
		 * 
		 * @param s char array of input string
		 */
		private String[] tokenMaker(String s)
		{
			char[] tok = s.toCharArray();
			String[] result = new String[tok.length];
			int current = 0;
			for (int i = 0; i < tok.length; i++)
			{
				if (!isDelimater(tok[i]))
				{
					String cat = "";
					int n = i;
					while (n < tok.length && !isDelimater(tok[n]))
					{//continue to add characters it a string 
					//till a delimiter is found
						String temp = cat;
						cat = temp + tok[n];
						n++;
					}
					result[current++] = cat;
					i = (n - 1);
				}
				else if (keepDelimaters)
					result[current++] = Character.toString(tok[i]);
			}
			//fix the length of the returned array
			String[] finalResult = new String[current];
			for(int i = 0; i < (current) ; i++)
			{
				finalResult[i] = result[i];
			}
			return finalResult;
		}

		/**
		 * Get status of tokens.
		 * 
		 * @return True if there are more tokens.
		 */
		public boolean hasMoreTokens()
		{
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
		public String nextToken()
		{
			if (tokenCount == tokensUsed)
				return null;
			else
				return tokens[tokensUsed++];
		}

		/**
		 * Returns true if testMe is a delimiter.  Determined
		 * by comparing it to every char in member delimiters.
		 * 
		 * @param testMe the char to be compared to
		 * @return True if char is a delimiter
		 */
		private boolean isDelimater(char testMe)
		{
			for (char c : delimaters)
			{
				if (testMe == c)
					return true;
			}
			return false;
		}
	}
	

	private Tokenizer tokens;
	private int tokenChar;
	private String tokenString;

	
	public static final int STRING_TOKEN = -1;
	public static final int EOLN_TOKEN = -2;

	/**
	 * Create a lexer.
	 * 
	 * @param s Lex this string.
	 */
	public Lexer(String s)
	{
		/**
		* We use a StringTokenizer to tokenize the string.
		* Our delimiters are the operators, parens, and 
		* white space.  By making the third parameter true
		* we instruct the StringTokenizer to return those
		* delimiters as tokens.
		*/

		tokens = new Tokenizer(
			s,
			new char[] {
				' ',
				'\t',
				'\n',
				'\r',
				':',
				'[',
				']',
				'(',
				')',
				'#',
				'~',
				'+',
				'-',
				','}, 
			true);

		/**
		* Start by advancing to the first token.  Note that 
		* this may get an error, which would set our 
		* errorMessage instead of setting tokenChar.
		*/

		advance();
	}

	/**
	* Advance to the next token.  We don't return
	* anything; the caller must use nextToken() to see
	* what that token is.
	*/
	public void advance()
	{

	// White space is returned as a token by our 
	// StringTokenizer, but we will loop until something 
	// other than white space has been found.

		while (true)
		{
			// If we're at the end, make it an EOLN_TOKEN.

			if (!tokens.hasMoreTokens())
			{
				tokenChar = EOLN_TOKEN;
				return;
			}

			// Get a token--if it looks like a number, 
			// make it a NUMBER_TOKEN.

			String s = tokens.nextToken();
			char[] temp = s.toCharArray();
			char c1 = temp[0];
			System.out.println(c1);
			if (s.length() > 1){
				tokenString = s;
				tokenChar = STRING_TOKEN;
				return;
			}
			// Any other single character that is not 
			// white space is a token.

			else if (s.length() == 1 && !isWhiteSpace(c1))
			{
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
	public String getString()
	{
		return tokenString;
	}

	/**
	 * Returns the next token as char
	 * 
	 * @return A token.
	 */
	public int nextToken()
	{
		return tokenChar;
	}
		
	/**
	 * Checks if a char is white space
	 * 
	 * @param Checkme Char to be checked
	 * @return True if char is whitespace
	 */
	private boolean isWhiteSpace(char Checkme) {
		char[] whitespace = new char[] {' ','\t','\n','\r'};
		for(char c : whitespace) 
			if(c == Checkme)
				return true;
		return false;
		
	}
}