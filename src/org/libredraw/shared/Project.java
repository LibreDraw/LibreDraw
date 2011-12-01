package org.libredraw.shared;

import java.io.Serializable;
import java.util.Date;

public class Project implements Serializable {
	public long m_id;
	public String m_name;
	public Date m_createdDate;
	public LDUser m_owner;
	public Date m_modified;
	public LDUser m_modifedBy;
	
	public Project(long id, String name, Date createdDate, LDUser owner, Date modified, LDUser modifiedBy) {
		m_id = id;
		m_name = name;
		m_createdDate = createdDate;
		m_owner = owner;
		m_modified = modified;
		m_modifedBy = modifiedBy;
	}
}
