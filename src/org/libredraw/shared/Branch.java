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

import java.util.Vector;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.googlecode.objectify.Key;

@Entity
public class Branch
{
	@Id public long id;
	public boolean locked;
	public boolean limited;
	
	public String m_name;
	transient public Key<LDUser> m_owner;
	transient public Key<Diagram> m_diagram;
	transient public Vector<Key<Version>> m_versions;
	
	@Transient public LDUser owner;
	@Transient public Diagram diagram;
	@Transient public Vector<Version> versions;
	
	public Branch() {
		
	}
	
	public Branch(long newId, String name, Key<LDUser> owner) {
		m_name = name;
		m_owner = owner;
		m_versions = new Vector<Key<Version>>();
		m_diagram = null;
	}
}
