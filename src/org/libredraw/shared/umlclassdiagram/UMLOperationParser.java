package org.libredraw.shared.umlclassdiagram;

public class UMLOperationParser {
	private Lexer lexer;
	private UMLOperation value;

	public UMLOperationParser(String s) throws Exception
	{
		lexer = new Lexer(s);

		value = parseExpression();
		
		match(Lexer.EOLN_TOKEN);

	}

	public UMLOperation getValue()
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
	private UMLOperation parseExpression() throws Exception {

		// <expression> ::= <visibility> <name>( <operands> ) :<returnType>
		
		UMLOperation result = new UMLOperation();
		result.m_visibility = Visibility();
		result = Name(result);
		match('(');
		result = Operands(result);
		match(')');
		match(':');
		result = returnType(result);

		return result;
	}

	private UMLOperation returnType(UMLOperation result) throws Exception {
		//<returnType> ::= String || String [ String ]
		if (lexer.nextToken() == Lexer.STRING_TOKEN) {
			result.m_returnType = lexer.getString();
			match(Lexer.STRING_TOKEN);
		}
		if(lexer.nextToken() == '[') {
			match('[');
			result.m_returnTypeMultiplicity = Multiplicity();
			match(']');
		}
		return result;
	}

	private UMLVisibility Visibility() throws Exception {
		
		//<visibility> ::= -|+|#|~|<eps>
				
			if (lexer.nextToken() == '-') {
				match('-');
				UMLVisibility result = UMLVisibility.Private;
				return result;
			}
			else if (lexer.nextToken() == '+')
			{
				match('+');
				UMLVisibility result = UMLVisibility.Public;
				return result;
			}
			else if (lexer.nextToken() == '#')
			{
				match('#');
				UMLVisibility result = UMLVisibility.Protected;
				return result;
			}
			else if (lexer.nextToken() == '~')
			{
				match('~');
				UMLVisibility result = UMLVisibility.Package;
				return result;
			}
			else 
				return UMLVisibility.Public;

	}
	
	private UMLOperation Name(UMLOperation result) throws Exception {
		//<name> ::= String
		if (lexer.nextToken() == Lexer.STRING_TOKEN) {
			result.m_name = lexer.getString();
			match(Lexer.STRING_TOKEN);
			return result;
		}
		return result;
	}
		
	private String Multiplicity() throws Exception {
		//<mult> ::= String
		if (lexer.nextToken() == Lexer.STRING_TOKEN) {
			String result = lexer.getString();
			match(Lexer.STRING_TOKEN);
			return result;
		}
		return null;
	}
	
	private UMLOperation Operands(UMLOperation result) throws Exception {
		// <operands> ::= <operation> || <operation> , <operands> || eps
		if(lexer.nextToken() != ')')
			result.m_parameters.add(Operand());
		else
			return result;
		if(lexer.nextToken() == ',') {
			match(',');
			result = Operands(result);
			return result;
		}
		else
			return result;
	}

	private UMLOperationParameter Operand() throws Exception {
		// <operand> ::= <op_name>: <op_type> [<mult>]
		UMLOperationParameter result = new UMLOperationParameter();
		result.m_name = Op_Name();
		match(':');
		result.m_type = Op_Type();
		if(lexer.nextToken() == '[') {
			match('[');
			result.m_multiplicity = Multiplicity();
			match(']');
		}

		return result;
	}

	private String Op_Type() throws Exception {
		//<op_type> ::= String
		if (lexer.nextToken() == Lexer.STRING_TOKEN) {
			String result = lexer.getString();
			match(Lexer.STRING_TOKEN);
			return result;
		}
		return null;
	}

	private String Op_Name() throws Exception {
		//<op_name> ::= String
		if (lexer.nextToken() == Lexer.STRING_TOKEN) {
			String result = lexer.getString();
			match(Lexer.STRING_TOKEN);
			return result;
		}
		return null;
	}

}
