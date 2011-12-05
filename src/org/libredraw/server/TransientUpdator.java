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

package org.libredraw.server;

import java.util.Date;
import java.util.Vector;
import org.libredraw.server.persistence.AutoIncrement;
import org.libredraw.server.persistence.DAO;
import org.libredraw.server.persistence.Permission;
import org.libredraw.shared.AccountConnector;
import org.libredraw.shared.Branch;
import org.libredraw.shared.Diagram;
import org.libredraw.shared.DiagramEntity;
import org.libredraw.shared.LDUser;
import org.libredraw.shared.PermissionRecord;
import org.libredraw.shared.Project;
import org.libredraw.shared.Version;
import org.libredraw.shared.umlclassdiagram.UMLAssociation;
import org.libredraw.shared.umlclassdiagram.UMLClass;
import org.libredraw.shared.umlclassdiagram.UMLInterface;
import org.libredraw.shared.umlclassdiagram.UMLOperation;
import org.libredraw.shared.umlclassdiagram.UMLAttribute;

import com.googlecode.objectify.Key;

public final class TransientUpdator {
	
	static DAO dba = new DAO();
	
	public static Project u(Project p) {
		p.owner = u((LDUser) dba.get(p.m_owner));
		if(p.m_diagrams == null || p.m_diagrams.isEmpty()) {
			p.modified = p.m_createdDate;
			p.modifiedBy = p.owner;
		}
		else {
			Date latest = null;
			Diagram latestD = null;
			for(Key<Diagram> k : p.m_diagrams) {
				Diagram d = (Diagram) dba.get(k);
				Date dDate = getModifiedDate(d);
				if(latest == null) {
					latest = dDate;
					latestD = d;
				}
				else {
					if(latest.before(dDate)) {
						latest = dDate;
						latestD = d;
					}
				}
			}
			p.modified = latest;
			p.modifiedBy = u(getModifiedBy(latestD));
		}
		return p;
	}
	
	public static Diagram u(Diagram d) {
		d.owner = u((LDUser) dba.get(d.m_owner));
		d.master = d.m_master.getId();
		Branch masterB = (Branch) dba.get(d.m_master);
		if(masterB.m_versions == null || masterB.m_versions.isEmpty()) {
			d.modifiedBy = d.owner;
			d.modifiedDate = d.m_createdDate;
		}
			
		else {
			Date latest = null;
			Key<?> latestU = null;
			for(Key<?> k : masterB.m_versions) {
				Version v = (Version) dba.get(k);
				if(latest == null) {
					latest = v.m_date;
					latestU = v.m_modifiedBy;
				}
				if(v.m_date.before(latest)) {
					latest = v.m_date;
					latestU = v.m_modifiedBy;
				}
			}
			d.modifiedBy = u((LDUser) dba.get(latestU));
			d.modifiedDate = latest;
		}
		return d;
	}
	
	public static LDUser u(LDUser u) {
		AccountConnector connector = (AccountConnector) dba.get(u.m_accountConnector);
		u.m_displayName = connector.m_displayName;
		return u;
	}
	
	public static UMLClass u(UMLClass c) {
		c.operations = new Vector<UMLOperation>();
		if(c.m_operations != null)
			for(Key<UMLOperation> o: c.m_operations) {
				c.operations.add((UMLOperation) dba.get(o));
			}
		c.attributes = new Vector<UMLAttribute>();
		if(c.m_attributes != null)
			for(Key<UMLAttribute> o: c.m_attributes) {
				c.attributes.add((UMLAttribute) dba.get(o));
			}
		return (UMLClass) u((DiagramEntity)c);
	}
	
	public static UMLInterface u(UMLInterface i) {
		i.operations = new Vector<UMLOperation>();
		for(Key<UMLOperation> o: i.m_operations) {
			i.operations.add((UMLOperation) dba.get(o));
		}
		i.attributes = new Vector<UMLAttribute>();
		for(Key<UMLAttribute> o: i.m_attributes) {
			i.attributes.add((UMLAttribute) dba.get(o));
		}
		return (UMLInterface) u((DiagramEntity)i);
	}
	
	public static UMLAssociation u(UMLAssociation a) {
		a.right = (DiagramEntity) dba.get(a.m_right);
		a.left = (DiagramEntity) dba.get(a.m_left);
		return (UMLAssociation) u((DiagramEntity)a);
	}
	
	@SuppressWarnings("unchecked")
	public static DiagramEntity u(DiagramEntity d) {
		d.modifiedBy = TransientUpdator.u((LDUser) dba.get(d.m_modifiedBy));
		d.createdBy = TransientUpdator.u((LDUser) dba.get(d.m_createdBy));
		if(d.m_lockedBy != null)
			d.lockedBy = TransientUpdator.u((LDUser) dba.get(d.m_lockedBy));
		if(d.m_limitedBy != null)
			d.limitedBy = TransientUpdator.u((LDUser) dba.get(d.m_limitedBy));
		
		if(d.getClass() == UMLClass.class) {
			UMLClass c = (UMLClass) d;
			d.entityKey = new Key<UMLClass>((Class<UMLClass>) d.getClass(), c.id);
		} else if(d.getClass() == UMLInterface.class) {
			UMLInterface c = (UMLInterface) d;
			d.entityKey = new Key<UMLInterface>((Class<UMLInterface>) d.getClass(), c.id);
		} else if(d.getClass() == UMLAssociation.class) {
			UMLAssociation c = (UMLAssociation) d;
			d.entityKey = new Key<UMLAssociation>((Class<UMLAssociation>) d.getClass(), c.id);
		}
		
		return d;
	}
	
	private static Date getModifiedDate(Diagram d) {
		Date latest = null;
		Branch masterB = (Branch) dba.get(d.m_master);
		if(masterB.m_versions == null || masterB.m_versions.isEmpty())
			latest = d.m_createdDate;
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

	private static LDUser getModifiedBy(Diagram d) {
		Key<?> latest = null;
		Branch masterB = (Branch) dba.get(d.m_master);
		if(masterB.m_versions == null || masterB.m_versions.isEmpty())
			latest = d.m_owner;
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
		return u((LDUser) dba.get(latest));
	}
	
	public static Version nextVersion(Key<Version> thisVersion, Key<LDUser> modified) {
		Vector<Key<DiagramEntity>> objects = new Vector<Key<DiagramEntity>>();
		Version v = (Version) dba.get(thisVersion);
		for(Key<DiagramEntity> k: v.m_objects) {
			objects.add(k);
		}
		return new Version(AutoIncrement.getNextId(Version.class), v.m_versionNuber+1, thisVersion , modified, objects);
	}
	
	@SuppressWarnings("unchecked")
	public static UMLClass p(UMLClass theClass, Key<LDUser> m) {
		theClass.id = AutoIncrement.getNextId(UMLClass.class);
		theClass.m_createdBy = new Key<LDUser>(LDUser.class, theClass.createdBy.id);
		theClass.m_modifiedBy = m;
		theClass.m_modified = new Date();
		theClass.m_operations = new Vector<Key<UMLOperation>>();
		if(theClass.m_operations != null)
			for(UMLOperation o: theClass.operations) {
				o.id = AutoIncrement.getNextId(UMLOperation.class);
				theClass.m_operations.add((Key<UMLOperation>) dba.put(o));
			}
		theClass.m_attributes = new Vector<Key<UMLAttribute>>();
		if(theClass.m_attributes != null)
			for(UMLAttribute a: theClass.attributes) {
				a.id = AutoIncrement.getNextId(UMLAttribute.class);
				theClass.m_attributes.add((Key<UMLAttribute>) dba.put(a));
			}
		
		return theClass;
	}
	
	public static int getPermission(PermissionRecord p) {
		int result = 0;
		if(p.READ)
			result += Permission.READ;
		if(p.WRITE)
			result += Permission.WRITE;
		if(p.BRANCH)
			result += Permission.BRANCH;
		if(p.MERGE)
			result += Permission.MERGE;
		if(p.EXPORT)
			result += Permission.EXPORT;
		if(p.OWNER)
			result += Permission.OWNER;
		return result;
	}

}
