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

class OperationParser extends Parser {

	public static Operation go(String s) throws ParserException {
		Operation value;
		lexer = new Lexer(s);

		value = parseExpression();

		match(Lexer.EOLN_TOKEN);
		
		return value;
	}

	/**
	 * Parse an expression. If any error occurs we return immediately.
	 * 
	 * @return The UMLOperation being built
	 * @throws ParserException
	 */
	private static Operation parseExpression() throws ParserException {

		// <expression> ::= <visibility> <name> ( <operands> ) :<returnType>
		// || <visibility> <name> ( <operands> )

		Operation result = new Operation();
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
	private static Operation returnType(Operation result) throws ParserException {
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
	private static  Visibility Visibility() throws ParserException {

		// <visibility> ::= -|+|#|~|<epsilon>

		if (lexer.nextToken() == '-') {
			match('-');
			Visibility result = Visibility.Private;
			return result;
		} else if (lexer.nextToken() == '+') {
			match('+');
			Visibility result = Visibility.Public;
			return result;
		} else if (lexer.nextToken() == '#') {
			match('#');
			Visibility result = Visibility.Protected;
			return result;
		} else if (lexer.nextToken() == '~') {
			match('~');
			Visibility result = Visibility.Package;
			return result;
		} else
			return Visibility.Public;

	}

	/**
	 * Parse the input string to get the name.
	 * 
	 * @param result
	 *            UMLOperation
	 * @return the UMLOperation being built
	 * @throws ParserException
	 */
	private static Operation Name(Operation result) throws ParserException {
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
	private static String Multiplicity() throws ParserException {
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
	private static Operation Operands(Operation result) throws ParserException {
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
	private static OperationParameter Operand() throws ParserException {
		// <operand> ::= <op_name>: <op_type> [<mult>]
		OperationParameter result = new OperationParameter();
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
	private static String Op_Type() throws ParserException {
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
	private static String Op_Name() throws ParserException {
		// <op_name> ::= String
		if (lexer.nextToken() == Lexer.STRING_TOKEN) {
			String result = lexer.getString();
			match(Lexer.STRING_TOKEN);
			return result;
		}
		throw new ParserException(lexer.getString());
	}

}
