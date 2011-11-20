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

import javax.persistence.Entity;
import javax.persistence.Id;

import com.googlecode.objectify.Key;

@Entity
public class P_Authorization {

	public Key<?> m_regarding;
	public boolean m_archive;
	public Key<P_LDUser> m_user;
	public int m_granted;
	
	@Id public long id;
	
	public P_Authorization(Key<P_LDUser> user, Key<?> regarding, int granted) {
		id = P_AutoIncrement.getNextId(this.getClass());
		m_user = user;
		m_regarding = regarding;
		m_granted = granted;
		m_archive = false;
	}

}
