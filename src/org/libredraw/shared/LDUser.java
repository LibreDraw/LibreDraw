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

package org.libredraw.shared;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.libredraw.server.persistence.AccountConnector;
import org.libredraw.server.persistence.AutoIncrement;
import org.libredraw.server.persistence.DAO;
import org.libredraw.server.persistence.GenericAccountConnector;
import org.libredraw.shared.LDUser;
import com.googlecode.objectify.Key;

@Entity
public class LDUser
{
	transient public Key<GenericAccountConnector> m_accountConnector;
	@Transient public String m_displayName;
	
	@Id public long id;
	
	public LDUser() {
		
	}
	
	public LDUser(Key<GenericAccountConnector> accountConnector) {
		id = AutoIncrement.getNextId(this.getClass());
		this.m_accountConnector = accountConnector;
	}
	
	public void update() {
		DAO dba = new DAO();
		AccountConnector connector = (AccountConnector) dba.get(m_accountConnector);
		m_displayName = connector.m_displayName;
	}
	
}
