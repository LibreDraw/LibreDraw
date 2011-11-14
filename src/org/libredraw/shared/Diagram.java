package org.libredraw.shared;

import java.util.Date;

public class Diagram {

	public boolean locked;
	public boolean limited;
	
	public String m_name;
	public Date m_createdDate;
	public Date m_modifiedDate;
	public LDUser m_owner;
	public DiagramType m_type;
	public long m_master;
}
