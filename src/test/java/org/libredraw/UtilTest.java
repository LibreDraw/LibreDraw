package org.libredraw;

import static org.junit.Assert.*;
import org.junit.Test;

public class UtilTest {

	@Test
	public void testSha1Java() {
		String hash, result;
		hash = Util.sha1Java("TEST_STRING:q0N4AFxMcRUqnNfnw2W60NEevtauAkvto" +
				"V8QAgHWy5vpn9IakmROu2hwuyNid7s");
		result = "2ee0757adfaf2076a73966666a198f7555954b1e";
		assertEquals(hash, result);
	}

}
