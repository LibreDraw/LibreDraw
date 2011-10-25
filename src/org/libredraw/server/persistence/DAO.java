package org.libredraw.server.persistence;

import com.googlecode.objectify.util.DAOBase;
import java.io.UnsupportedEncodingException; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 

public class DAO extends DAOBase {
	/*
	 * This function credit to:
	 * <code>http://www.anyexample.com/programming/java/java_simple_class_to_compute_sha_1_hash.xml</code
	 */
	private String hashPassword(String password)
		throws NoSuchAlgorithmException, UnsupportedEncodingException  { 
		    MessageDigest md;
		    md = MessageDigest.getInstance("SHA-1");
		    byte[] sha1hash = new byte[40];
		    md.update(password.getBytes("iso-8859-1"), 0, password.length());
		    sha1hash = md.digest();
		    return convertToHex(sha1hash);
	}
	
	/*
	 * This function credit to:
	 * <code>http://www.anyexample.com/programming/java/java_simple_class_to_compute_sha_1_hash.xml</code
	 */
	private String convertToHex(byte[] data) { 
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) { 
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do { 
                if ((0 <= halfbyte) && (halfbyte <= 9)) 
                    buf.append((char) ('0' + halfbyte));
                else 
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        } 
        return buf.toString();
    } 
}
