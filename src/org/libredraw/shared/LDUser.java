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

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.libredraw.shared.LDUser;
import com.googlecode.objectify.Key;

@Entity
public class LDUser implements Serializable {
	private static final long serialVersionUID = 1L;
	transient public Key<GenericAccountConnector> m_accountConnector;
	@Transient public String m_displayName;
	
	@Id public long id;
	
	public LDUser() {
		
	}
	
	public LDUser(long newId, Key<GenericAccountConnector> accountConnector) {
		id = newId;
		this.m_accountConnector = accountConnector;
	}
	
}
