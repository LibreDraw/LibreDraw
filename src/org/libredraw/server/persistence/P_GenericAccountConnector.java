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

package org.libredraw.server.persistence;

import java.security.MessageDigest;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class P_GenericAccountConnector extends P_AccountConnector
{

	public String m_password;
	public String m_salt;
	public String m_email;
	
	@Id public long id;
	public boolean locked;
	public boolean limited;
	
	public P_GenericAccountConnector(String email, String password, String name) 
			throws Exception {
		super(name);
		this.m_email = email;
		this.m_password = hashPassword(this.m_salt + password);
	}
	
	public boolean checkPassword(String password) {
		if(hashPassword(this.m_salt + password) == this.m_password)
			return true;
		return false;
	}
	
	/*
	 * This function credit to:
	 * <code>http://www.anyexample.com/programming/java/java_simple_class_to_compute_sha_1_hash.xml</code
	 */
	private String hashPassword(String password) {
		    MessageDigest md;
		    byte[] sha1hash = new byte[40];
		    try {
		    	md = MessageDigest.getInstance("SHA-1");
			    md.update(password.getBytes("iso-8859-1"), 0, password.length());
			    sha1hash = md.digest();
		    } catch (Exception e) {
		    	
		    }

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
