package org.libredraw.parser;

public class Parser {
	protected Lexer lexer;

	/**
	 * Match a given token and advance to the next. This utility is used by our
	 * parsing routines. If the given token does not match lexer.nextToken(), we
	 * generate an appropriate error message. Advancing to the next token may
	 * also cause an error.
	 * 
	 * @param token
	 *            the token that must match
	 * @throws ParserException
	 */
	protected void match(int token) throws ParserException {
		if (lexer.nextToken() == token) {
			lexer.advance();
		} else
			throw new ParserException(lexer.getString());
	}
}
