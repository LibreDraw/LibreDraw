package org.libredraw.parser;

public class ParserException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Builds a ParserException.
	 * 
	 * @param msg
	 *            Token that was not expected.
	 */
	public ParserException(String msg) {
		super("Unexpected Character: " + msg);
	}
}
