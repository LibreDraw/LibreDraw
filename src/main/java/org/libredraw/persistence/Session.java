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

package org.libredraw.persistence;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;



import com.googlecode.objectify.Key;

@Entity
public class Session {

	@Id public long id;
	public String m_sessionId;
	public Key<LDUser> m_user;
	public Date m_ttl;
	
	public Session() {
		
	}

	public Session(String sessionId, Key<LDUser> user) {
		id = AutoIncrement.getNextId(this.getClass());
		m_sessionId = sessionId;
		m_user = user;
		m_ttl = new Date(new Date().getTime() + 1000 * 60 * 60 * 24 * 14);
	}

	public boolean checkSession(String sessionId) {
		if(m_sessionId.equals(sessionId)) {
			if(m_ttl.after(new Date())) {
				return true;
			}
		}
		return false;
	}

}
