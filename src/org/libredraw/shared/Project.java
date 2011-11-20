package org.libredraw.shared;

import java.util.Date;
import java.util.Vector;

public class Project {
	public long m_id;
	public String m_name;
	public Date m_createdDate;
	public Vector<Long> m_diagrams;
	public LDUser m_owner;
	public Date m_modified;
	public LDUser m_modifedBy;
	
	public Project(long id, String name, Date createdDate, LDUser owner, Date modified, LDUser modifiedBy) {
		m_name = name;
		m_createdDate = createdDate;
		m_owner = owner;
		m_modified = modified;
		m_modifedBy = modifiedBy;
	}
}
