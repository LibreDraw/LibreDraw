package org.libredraw.server.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

public class P_Project extends P_AutoIncrement {

	public String m_name;
	public Date m_createdDate;
	public Vector<P_Key> m_diagrams;
	public P_Key m_owner;
	
	public P_Project(String name, P_Key owner) throws Exception {
		super();
		this.m_name = name;
		this.m_createdDate = new Date();
		this.m_diagrams = new Vector<P_Key>();
		this.m_owner = owner;
	}
	
}
