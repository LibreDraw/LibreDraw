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
import javax.persistence.Id;

import org.libredraw.shared.Diagram;
import org.libredraw.shared.DiagramType;

import com.googlecode.objectify.Key;

public class P_Diagram
{
	
	@Id public long id;
	public boolean locked;
	public boolean limited;
	
	public String m_name;
	public Date m_createdDate;
	public Date m_modifiedDate;
	public Key<?> m_owner;
	public DiagramType m_type;
	public Key<?> m_master;
	public Key<?> m_modifiedBy;
	
	public P_Diagram(String name, DiagramType type, Key<?> owner, Key<?> master) {
		id = P_AutoIncrement.getNextId(this.getClass());
		m_name = name;
		m_owner = owner;
		m_modifiedBy = owner;
		m_createdDate = new Date();
		m_modifiedDate = new Date();
		m_master = master;
		m_type = type;
	}
	
	public Diagram getShareable() {
		//TODO implement
		return null;
	}
}
