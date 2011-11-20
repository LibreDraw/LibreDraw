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

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.libredraw.server.Util;

@Entity
public class P_GenericAccountConnector extends P_AccountConnector
{

	public String m_password;
	public String m_salt;
	public String m_email;
	
	@Id public long id;
	
	public P_GenericAccountConnector() {
	}
	
	public P_GenericAccountConnector(String email, String password, String name) {
		super(name);
		id = P_AutoIncrement.getNextId(this.getClass());
		m_email = email;
		m_salt = Util.sha1(new Date().toString() + m_email);
		m_password = Util.sha1(this.m_salt + password);
	}
	
	public boolean checkPassword(String password) {
		password = Util.sha1(this.m_salt + password);
		if(m_password.equals(password))
			return true;
		else
			return false;
	}
}
