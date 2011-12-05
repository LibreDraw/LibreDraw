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
import javax.persistence.Transient;
import com.googlecode.objectify.Key;

public abstract class DiagramEntity implements Serializable {

	private static final long serialVersionUID = 1805626351345492823L;
	public int m_xCoordinate;
	public int m_yCoordinate;
	public int m_width;
	public int m_height;
	
	public boolean m_locked;
	public boolean m_limited;
	transient public Key<LDUser> m_lockedBy;
	transient public Key<LDUser> m_limitedBy;
	public String m_name;
	public Date m_created;
	public Date m_modified;
	transient public Key<LDUser> m_modifiedBy;
	transient public Key<LDUser> m_createdBy;
	
	@Transient public LDUser lockedBy;
	@Transient public LDUser limitedBy;
	@Transient public LDUser modifiedBy;
	@Transient public LDUser createdBy;
	@Transient public Key<?> entityKey;
	
	public DiagramEntity() {
		
	}
	
	public DiagramEntity(String name, Key<LDUser> createdBy) {
		m_name = name;
		m_created = new Date();
		m_modified = new Date();
		m_createdBy = createdBy;
		m_modifiedBy = createdBy;
	}
}
