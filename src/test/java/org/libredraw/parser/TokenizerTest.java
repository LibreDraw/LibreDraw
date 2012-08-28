package org.libredraw.parser;

import static org.junit.Assert.*;
import org.junit.Test;
import org.libredraw.parser.Tokenizer;

public class TokenizerTest {

	@Test
	public void testTokenizer() {
		char[] delim = {
				',',
				' ',
				'\n',
				'\r',
				'\t',
				'(',
				')'};
		
		String test = "5,6,7,8,9,(cat,dog,tree,buffer)";
		Tokenizer token = new Tokenizer(test, delim, false);
		
		assertEquals(token.nextToken(), "5");
		assertEquals(token.nextToken(), "6");
		assertEquals(token.nextToken(), "7");
		assertEquals(token.nextToken(), "8");
		assertEquals(token.nextToken(), "9");
		
		assertEquals(token.nextToken(), "cat");
		assertEquals(token.nextToken(), "dog");
		assertEquals(token.nextToken(), "tree");
		assertEquals(token.nextToken(), "buffer");
		assertEquals(token.hasMoreTokens(),false);
		
		test = "cat,dog,tree,buffer";
		token = new Tokenizer(test, delim, true);

		assertEquals(token.nextToken(), "cat");
		assertEquals(token.nextToken(), ",");
		assertEquals(token.nextToken(), "dog");
		assertEquals(token.nextToken(), ",");
		assertEquals(token.nextToken(), "tree");
		assertEquals(token.nextToken(), ",");
		assertEquals(token.nextToken(), "buffer");
		assertEquals(token.hasMoreTokens(),false);
	}
}
