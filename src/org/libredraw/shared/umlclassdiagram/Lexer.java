package org.libredraw.shared.umlclassdiagram;


public class Lexer
{
	
	private class Tokenizer
	{
		/**
		 * The list of delimaters that the constructor provides. 
		 */ 
		private char[] delimaters;

		/**
		 * The tokens that are delimiated by tokemMaker.
		 */
		private String[] tokens;

		/**
		 * Count of how many tokens are avalable.
		 */
		private int tokenCount;

		/**
		 * Stores what tokens have been returned.
		 */
		private int tokensUsed;

		/**
		 * Determines whether or not the delimaters are to
		 * be returned as delimaters.
		 */
		private boolean keepDelimaters;

		/**
		 * Tokenizer constructor initalizes member variables
		 * and delimiates the string s to tokens using in_delimates.
		 * 
		 * @param s string to be tokenized
		 * @param in_delimaters list of delimaters
		 * @param in_keepDelimaters whether or not to include delimaters
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
		 * 2. progress down the array creating strings between delimaters.
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
					//till a delimater is found
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
		 * Returns bool true if the Tokenizer has more tokesn.
		 */
		public boolean hasMoreTokens()
		{
			if (tokenCount == tokensUsed)
				return false;
			else
				return true;
		}

		/**
		 * Returns current token and inrements tokensUsed.
		 */
		public String nextToken()
		{
			if (tokenCount == tokensUsed)
				return null;
			else
				return tokens[tokensUsed++];
		}

		/**
		 * Returns true if testMe is a delimater.  Determined
		 * by comparing it to every char in member delimaters.
		 * 
		 * @param testMe the char to be compared to
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
		s,new char[] {' ','\t','\n','\r',':','[',']','(',')','#','~','+','-',','}, true);

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

	public String getString()
	{
		return tokenString;
	}

	public int nextToken()
	{
		return tokenChar;
	}
		
	private boolean isWhiteSpace(char Checkme) {
		char[] whitespace = new char[] {' ','\t','\n','\r'};
		for(char c : whitespace) 
			if(c == Checkme)
				return true;
		return false;
		
	}
}