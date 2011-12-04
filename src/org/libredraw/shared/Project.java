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
import java.util.Date;
import java.util.Vector;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.libredraw.shared.Project;
import com.googlecode.objectify.Key;

@Entity
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id public long id;
	public boolean locked;
	public boolean limited;
	
	public String m_name;
	public Date m_createdDate;
	transient public Vector<Key<Diagram>> m_diagrams;
	transient public Key<LDUser> m_owner;
	
	@Transient public LDUser owner;
	@Transient public Date modified;
	@Transient public LDUser modifiedBy;
	

	
	public Project() {
		
	}
	
	public Project(long newId, String name, Key<LDUser> owner) {
		id = newId;
		m_name = name;
		m_createdDate = new Date();
		m_diagrams = new Vector<Key<Diagram>>();
		m_owner = owner;
	}
	
	public void addDiagram(Key<Diagram> diagram) {
		if(m_diagrams == null)
			m_diagrams = new Vector<Key<Diagram>>();
		m_diagrams.add(diagram);
	}
}
