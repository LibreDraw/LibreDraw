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

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.libredraw.server.persistence.AutoIncrement;
import org.libredraw.server.persistence.DAO;
import org.libredraw.shared.Diagram;
import com.googlecode.objectify.Key;

@Entity
public class Diagram
{
	@Id public long id;
	public boolean locked;
	public boolean limited;
	
	public String m_name;
	public Date m_createdDate;
	transient public Key<?> m_owner;
	public DiagramType m_type;
	transient public Key<?> m_master;
	transient public Key<?> m_modifiedBy;
	
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
	
	public Diagram(String name, DiagramType type, Key<?> owner, Key<?> master) {
		id = AutoIncrement.getNextId(this.getClass());
		m_name = name;
		m_owner = owner;
		m_modifiedBy = owner;
		m_createdDate = new Date();
		m_master = master;
		m_type = type;
	}
	
	public void update() {
		DAO dba = new DAO();
		owner = (LDUser) dba.get(m_owner);
		owner.update();
		master = m_master.getId();
		Branch masterB = (Branch) dba.get(m_master);
		if(masterB.m_versions == null || masterB.m_versions.isEmpty()) {
			modifiedBy = owner;
			modifiedDate = m_createdDate;
		}
			
		else {
			Date latest = null;
			for(Key<?> k : masterB.m_versions) {
				Version v = (Version) dba.get(k);
				if(latest == null)
					latest = v.m_date;
				if(v.m_date.before(latest))
					latest = v.m_date;
			}
			modifiedDate = latest;
		}
		
	}



	public Date getModifiedDate() {
		DAO dba = new DAO();
		Date latest = null;
		Branch masterB = (Branch) dba.get(m_master);
		if(masterB.m_versions == null || masterB.m_versions.isEmpty())
			latest = m_createdDate;
		else {
			for(Key<?> k : masterB.m_versions) {
				Version v = (Version) dba.get(k);
				if(latest == null)
					latest = v.m_date;
				if(v.m_date.before(latest))
					latest = v.m_date;
			}
		}
		return latest;
	}

	public LDUser getModifiedBy() {
		DAO dba = new DAO();
		Key<?> latest = null;
		Branch masterB = (Branch) dba.get(m_master);
		if(masterB.m_versions == null || masterB.m_versions.isEmpty())
			latest = m_owner;
		else {
			Date last = null;
			for(Key<?> k : masterB.m_versions) {
				Version v = (Version) dba.get(k);
				if(last == null) {
					last = v.m_date;
					latest = v.m_modifiedBy;
				}
				if(v.m_date.before(last)) {
					last = v.m_date;
					latest = v.m_modifiedBy;
				}
			}
		}
		LDUser thisOne = (LDUser) dba.get(latest);
		thisOne.update();
		return thisOne;
	}
}
