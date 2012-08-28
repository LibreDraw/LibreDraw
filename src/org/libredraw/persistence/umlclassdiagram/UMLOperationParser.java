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

public class UMLOperationParser extends Parser {

	private Lexer lexer;
	private UMLOperation value;

	/**
	 * Create an operation parser, parse the string s.
	 * 
	 * @param s
	 *            String to be parsed.
	 * @throws ParserException
	 */
	public UMLOperationParser(String s) throws ParserException {
		lexer = new Lexer(s);

		value = parseExpression();

		match(Lexer.EOLN_TOKEN);
	}

	/**
	 * Get the result of the parse.
	 * 
	 * @return UMLOperation that was built from the input string.
	 */
	public UMLOperation getValue() {
		return value;
	}

	/**
	 * Parse an expression. If any error occurs we return immediately.
	 * 
	 * @return The UMLOperation being built
	 * @throws ParserException
	 */
	private UMLOperation parseExpression() throws ParserException {

		// <expression> ::= <visibility> <name> ( <operands> ) :<returnType>
		// || <visibility> <name> ( <operands> )

		UMLOperation result = new UMLOperation();
		result.m_visibility = Visibility();
		result = Name(result);
		match('(');
		result = Operands(result);
		match(')');
		if (lexer.nextToken() == ':') {
			match(':');
			result = returnType(result);
		}

		return result;
	}

	/**
	 * Parse the input string to get the return type.
	 * 
	 * @param result
	 * @return UMLOperation that is being built
	 * @throws ParserException
	 */
	private UMLOperation returnType(UMLOperation result) throws ParserException {
		// <returnType> ::= String || String [ String ]
		if (lexer.nextToken() == Lexer.STRING_TOKEN) {
			result.m_returnType = lexer.getString();
			match(Lexer.STRING_TOKEN);
		}
		if (lexer.nextToken() == ')')
			return result;
		if (lexer.nextToken() == '[') {
			match('[');
			result.m_returnTypeMultiplicity = Multiplicity();
			match(']');
		}
		return result;
	}

	/**
	 * Parse the input string to get the visibility.
	 * 
	 * @return The UMLVisibility that was built
	 * @throws ParserException
	 */
	private UMLVisibility Visibility() throws ParserException {

		// <visibility> ::= -|+|#|~|<epsilon>

		if (lexer.nextToken() == '-') {
			match('-');
			UMLVisibility result = UMLVisibility.Private;
			return result;
		} else if (lexer.nextToken() == '+') {
			match('+');
			UMLVisibility result = UMLVisibility.Public;
			return result;
		} else if (lexer.nextToken() == '#') {
			match('#');
			UMLVisibility result = UMLVisibility.Protected;
			return result;
		} else if (lexer.nextToken() == '~') {
			match('~');
			UMLVisibility result = UMLVisibility.Package;
			return result;
		} else
			return UMLVisibility.Public;

	}

	/**
	 * Parse the input string to get the name.
	 * 
	 * @param result
	 *            UMLOperation
	 * @return the UMLOperation being built
	 * @throws ParserException
	 */
	private UMLOperation Name(UMLOperation result) throws ParserException {
		// <name> ::= String
		if (lexer.nextToken() == Lexer.STRING_TOKEN) {
			result.m_name = lexer.getString();
			match(Lexer.STRING_TOKEN);
			return result;
		}
		return result;
	}

	/**
	 * Parse the input string to get the multiplicity.
	 * 
	 * @return String that represents the multiplicity.
	 * @throws ParserException
	 */
	private String Multiplicity() throws ParserException {
		// <mult> ::= String
		if (lexer.nextToken() == Lexer.STRING_TOKEN) {
			String result = lexer.getString();
			match(Lexer.STRING_TOKEN);
			return result;
		}
		throw new ParserException(lexer.getString());
	}

	/**
	 * Parse the input string to get the operand(s).
	 * 
	 * @param result
	 * @return The UMLOperation being built.
	 * @throws ParserException
	 */
	private UMLOperation Operands(UMLOperation result) throws ParserException {
		// <operands> ::= <operation> || <operation> , <operands> || epsilon
		if (lexer.nextToken() != ')')
			result.m_parameters.add(Operand());
		else
			return result;
		if (lexer.nextToken() == ',') {
			match(',');
			result = Operands(result);
			return result;
		} else
			return result;
	}

	/**
	 * Parse the input string to get the operation parameter.
	 * 
	 * @return The UMLOperationParamater being built.
	 * @throws ParserException
	 */
	private UMLOperationParameter Operand() throws ParserException {
		// <operand> ::= <op_name>: <op_type> [<mult>]
		UMLOperationParameter result = new UMLOperationParameter();
		result.m_name = Op_Name();
		match(':');
		result.m_type = Op_Type();
		if (lexer.nextToken() == '[') {
			match('[');
			result.m_multiplicity = Multiplicity();
			match(']');
		}

		return result;
	}

	/**
	 * Gets the operation parameter type.
	 * 
	 * @return String that represents a operator type.
	 * @throws ParserException
	 */
	private String Op_Type() throws ParserException {
		// <op_type> ::= String
		if (lexer.nextToken() == Lexer.STRING_TOKEN) {
			String result = lexer.getString();
			match(Lexer.STRING_TOKEN);
			return result;
		}
		throw new ParserException(lexer.getString());
	}

	/**
	 * gets the operation parameter name.
	 * 
	 * @return String that represents a operand name
	 * @throws ParserException
	 */
	private String Op_Name() throws ParserException {
		// <op_name> ::= String
		if (lexer.nextToken() == Lexer.STRING_TOKEN) {
			String result = lexer.getString();
			match(Lexer.STRING_TOKEN);
			return result;
		}
		throw new ParserException(lexer.getString());
	}

}
