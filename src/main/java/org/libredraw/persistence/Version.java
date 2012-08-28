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
import java.util.Vector;
import javax.persistence.Entity;
import javax.persistence.Id;


import com.googlecode.objectify.Key;

@Entity
public class Version {
	@Id public long id;
	public boolean locked;
	public boolean limited;
	
	public String m_tag;
	public int m_versionNuber;
	public Date m_date;
	public Vector<Key<DiagramEntity>> m_objects;
	public Key<Version> m_previousVersion;
	public Key<LDUser> m_modifiedBy;
	
	public Version() {
	}
	
	public Version(long newId, int versionNumber, Key<Version> previous, Key<LDUser> modified) {
		id = newId;
		m_tag = "";
		m_versionNuber = versionNumber;
		m_date = new Date();
		m_objects = new Vector<Key<DiagramEntity>>();
		m_previousVersion = previous;
		m_modifiedBy = modified;
	}
	
	public Version(long newId, int versionNumber, Key<Version> previous, Key<LDUser> modified, Vector<Key<DiagramEntity>> objects) {
		id = newId;
		m_tag = "";
		m_versionNuber = versionNumber;
		m_date = new Date();
		m_objects = objects;
		m_previousVersion = previous;
		m_modifiedBy = modified;
	}
	
	public void add(Key<DiagramEntity> k) {
		if(m_objects == null)
			m_objects = new Vector<Key<DiagramEntity>>();
		m_objects.add(k);
	}
}
