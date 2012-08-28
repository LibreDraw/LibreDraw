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

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.libredraw.persistence.Diagram;

import com.googlecode.objectify.Key;

@Entity
public class Diagram implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id public long id;
	public boolean locked;
	public boolean limited;
	
	public String m_name;
	public Date m_createdDate;
	transient public Key<LDUser> m_owner;
	public DiagramType m_type;
	transient public Key<Branch> m_master;
	transient public Key<LDUser> m_modifiedBy;
	
	@Transient 
	public LDUser owner;
	@Transient 
	public long master;
	@Transient
	public LDUser modifiedBy;
	@Transient
	public Date modifiedDate;
	
	public Diagram() {
		
	}
	
	public Diagram(long newId, String name, DiagramType type, Key<LDUser> owner, Key<Branch> master) {
		id = newId;
		m_name = name;
		m_owner = owner;
		m_modifiedBy = owner;
		m_createdDate = new Date();
		m_master = master;
		m_type = type;
	}
}
