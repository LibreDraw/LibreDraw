package org.libredraw.shared.umlclassdiagram;

public class UMLAttributeParser {
	private Lexer lexer;
	private UMLAttribute value;

	public UMLAttributeParser(String s) throws Exception
	{
		lexer = new Lexer(s);

		value = parseExpression();
		
		match(Lexer.EOLN_TOKEN);

	}

	public UMLAttribute getValue()
	{
		return value;
	}

	/**
	 * Match a given token and advance to the next.  
	 * This utility is used by our parsing routines.  
	 * If the given token does not match
	 * lexer.nextToken(), we generate an appropriate
	 * error message.  Advancing to the next token may
	 * also cause an error.
	 *
	 * @param token the token that must match
	 * @throws Exception 
	 */
	private void match(int token) throws Exception {
		if (lexer.nextToken() == token)
		{
			lexer.advance();
		}
		else
			throw new Exception("Invalid Attribute");
	}

	/**
	 * Parse an expression.  If any error occurs we 
	 * return immediately.
	 *
	 * @return the double value of the expression 
	 * or garbage in case of errors.
	 * @throws Exception 
	 */
	private UMLAttribute parseExpression() throws Exception {

		// <expression> ::= <visibility> <name>:<type>[<mult>]

		UMLAttribute result = Visibility();
		result = Name(result);
		match(':');
		result = Type(result);
		if(lexer.nextToken()!=Lexer.EOLN_TOKEN) {
			match('[');
			result = Multiplicity(result);
			match(']');
		}
		
		return result;
	}
	
	private UMLAttribute Visibility() throws Exception {
		
		//<visibility> ::= -|+|#|~|<eps>
		
		UMLAttribute result = new UMLAttribute();
		
			if (lexer.nextToken() == '-') {
				match('-');
				result.m_visibility = UMLVisibility.Private;
				return result;
			}
			else if (lexer.nextToken() == '+')
			{
				match('+');
				result.m_visibility = UMLVisibility.Public;
				return result;
			}
			else if (lexer.nextToken() == '#')
			{
				match('#');
				result.m_visibility = UMLVisibility.Protected;
				return result;
			}
			else if (lexer.nextToken() == '~')
			{
				match('~');
				result.m_visibility = UMLVisibility.Package;
				return result;
			}
			else
				result.m_visibility = UMLVisibility.Public;
				return result;

	}
	
	private UMLAttribute Name(UMLAttribute result) throws Exception {
		//<name> ::= String
		if (lexer.nextToken() == Lexer.STRING_TOKEN) {
			result.m_name = lexer.getString();
			match(Lexer.STRING_TOKEN);
			return result;
		}
		return result;
	}
	
	private UMLAttribute Type(UMLAttribute result) throws Exception {
		//<type> ::= String
		if (lexer.nextToken() == Lexer.STRING_TOKEN) {
			result.m_type = lexer.getString();
			match(Lexer.STRING_TOKEN);
			return result;
		}
		return result;
	}
	
	private UMLAttribute Multiplicity(UMLAttribute result) throws Exception {
		//<mult> ::= String
		if (lexer.nextToken() == Lexer.STRING_TOKEN) {
			result.m_multiplicity = lexer.getString();
			match(Lexer.STRING_TOKEN);
			return result;
		}
		return result;
	}

}
