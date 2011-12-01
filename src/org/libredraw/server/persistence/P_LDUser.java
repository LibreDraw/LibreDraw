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
import org.libredraw.shared.LDUser;
import com.googlecode.objectify.Key;

@Entity
public class P_LDUser
{

	public Key<P_GenericAccountConnector> m_accountConnector;
	
	@Id public long id;
	
	public P_LDUser() {
		
	}
	
	public P_LDUser(Key<P_GenericAccountConnector> accountConnector) {
		id = P_AutoIncrement.getNextId(this.getClass());
		this.m_accountConnector = accountConnector;
	}
	
	public LDUser getShareable() {
		DAO dba = new DAO();
		P_AccountConnector connector = 
				(P_AccountConnector) dba.get(m_accountConnector);
		return new LDUser(connector.m_displayName);
	}
		
}
