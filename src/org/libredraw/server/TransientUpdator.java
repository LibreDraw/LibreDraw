package org.libredraw.server;

import java.util.Date;
import java.util.Vector;

import org.libredraw.server.persistence.AutoIncrement;
import org.libredraw.server.persistence.DAO;
import org.libredraw.shared.AccountConnector;
import org.libredraw.shared.Branch;
import org.libredraw.shared.Diagram;
import org.libredraw.shared.DiagramEntity;
import org.libredraw.shared.LDUser;
import org.libredraw.shared.Project;
import org.libredraw.shared.Version;
import org.libredraw.shared.umlclassdiagram.UMLAssociation;
import org.libredraw.shared.umlclassdiagram.UMLClass;
import org.libredraw.shared.umlclassdiagram.UMLInterface;

import com.googlecode.objectify.Key;

public final class TransientUpdator {
	
	static DAO dba = new DAO();
	
	public static Project update(Project p) {
		p.owner = update((LDUser) dba.get(p.m_owner));
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
			p.modifiedBy = update(getModifiedBy(latestD));
		}
		return p;
	}
	
	public static Diagram update(Diagram d) {
		d.owner = update((LDUser) dba.get(d.m_owner));
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
			d.modifiedBy = update((LDUser) dba.get(latestU));
			d.modifiedDate = latest;
		}
		return d;
	}
	
	public static LDUser update(LDUser u) {
		AccountConnector connector = (AccountConnector) dba.get(u.m_accountConnector);
		u.m_displayName = connector.m_displayName;
		return u;
	}
	
	@SuppressWarnings("unchecked")
	public static DiagramEntity update(DiagramEntity d) {
		d.createdBy = TransientUpdator.update((LDUser) dba.get(d.m_createdBy));
		d.modifiedBy = TransientUpdator.update((LDUser) dba.get(d.m_modifiedBy));
		d.lockedBy = TransientUpdator.update((LDUser) dba.get(d.m_lockedBy));
		
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
		return update((LDUser) dba.get(latest));
	}
	
	public static Version nextVersion(Key<Version> thisVersion, Key<LDUser> modified) {
		Vector<Key<DiagramEntity>> objects = new Vector<Key<DiagramEntity>>();
		Version v = (Version) dba.get(thisVersion);
		for(Key<DiagramEntity> k: v.m_objects) {
			objects.add(k);
		}
		return new Version(AutoIncrement.getNextId(Version.class), v.m_versionNuber+1, thisVersion , modified, objects);
	}

}
