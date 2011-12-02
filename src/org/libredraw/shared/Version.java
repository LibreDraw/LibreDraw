package org.libredraw.shared;

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
	
	public Version(int versionNumber, Key<Version> previous, Key<LDUser> modified) {
		m_tag = "";
		m_versionNuber = versionNumber;
		m_date = new Date();
		m_objects = new Vector<Key<DiagramEntity>>();
		m_previousVersion = previous;
		m_modifiedBy = modified;
	}
	
	public Version(int versionNumber, Key<Version> previous, Key<LDUser> modified, Vector<Key<DiagramEntity>> objects) {
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
