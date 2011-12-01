package org.libredraw.shared;

import java.io.Serializable;
import java.util.Date;

public class Diagram implements Serializable {

	public boolean locked;
	public boolean limited;
	
	public long m_id;
	public String m_name;
	public Date m_createdDate;
	public Date m_modifiedDate;
	public LDUser m_owner;
	public LDUser m_modifiedBy;
	public DiagramType m_type;
	public long m_master;
	
	public Diagram(){
	}
	
	public Diagram(long id, String name, Date creationDate, Date modifiedDate, LDUser modifiedBy, LDUser owner, DiagramType type, long master) {
		m_id = id;
		m_name = name;
		m_createdDate = creationDate;
		m_modifiedDate = modifiedDate;
		m_modifiedBy = modifiedBy;
		m_owner = owner;
		m_type = type;
		m_master = master;
	}
}
