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

import org.libredraw.parser.*;

class AttributeParser extends Parser {

	public static Attribute go(String s) throws ParserException {
		lexer = new Lexer(s);
		Attribute value;

		value = parseExpression();

		match(Lexer.EOLN_TOKEN);
		
		return value;
	}

	/**
	 * Parse an expression. If any error occurs we return immediately.
	 * 
	 * @return the double value of the expression or garbage in case of errors.
	 * @throws ParserException
	 */
	private static Attribute parseExpression() throws ParserException {

		// <expression> ::= <visibility> <name>:<type>[<mult>]

		Attribute result = Visibility();
		result = Name(result);
		match(':');
		result = Type(result);
		if (lexer.nextToken() != Lexer.EOLN_TOKEN) {
			match('[');
			result = Multiplicity(result);
			match(']');
		}

		return result;
	}

	private static Attribute Visibility() throws ParserException {

		// <visibility> ::= -|+|#|~|<eps>

		Attribute result = new Attribute();

		if (lexer.nextToken() == '-') {
			match('-');
			result.m_visibility = Visibility.Private;
			return result;
		} else if (lexer.nextToken() == '+') {
			match('+');
			result.m_visibility = Visibility.Public;
			return result;
		} else if (lexer.nextToken() == '#') {
			match('#');
			result.m_visibility = Visibility.Protected;
			return result;
		} else if (lexer.nextToken() == '~') {
			match('~');
			result.m_visibility = Visibility.Package;
			return result;
		} else
			result.m_visibility = Visibility.Public;
		return result;

	}

	private static Attribute Name(Attribute result) throws ParserException {
		// <name> ::= String
		if (lexer.nextToken() == Lexer.STRING_TOKEN) {
			result.m_name = lexer.getString();
			match(Lexer.STRING_TOKEN);
			return result;
		}
		return result;
	}

	private static Attribute Type(Attribute result) throws ParserException {
		// <type> ::= String
		if (lexer.nextToken() == Lexer.STRING_TOKEN) {
			result.m_type = lexer.getString();
			match(Lexer.STRING_TOKEN);
			return result;
		}
		return result;
	}

	private static Attribute Multiplicity(Attribute result)
			throws ParserException {
		// <mult> ::= String
		if (lexer.nextToken() == Lexer.STRING_TOKEN) {
			result.m_multiplicity = lexer.getString();
			match(Lexer.STRING_TOKEN);
			return result;
		}
		return result;
	}

}