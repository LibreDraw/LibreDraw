package org.libredraw.server.persistence;

import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.libredraw.shared.LDUser;
import org.libredraw.shared.Project;

import com.googlecode.objectify.Key;

@Entity
public class P_Project {
	
	DAO dba = new DAO();

	public String m_name;
	public Date m_createdDate;
	public Vector<Key<P_Diagram>> m_diagrams;
	public Key<P_LDUser> m_owner;
	
	@Id public long id;
	public boolean locked;
	public boolean limited;
	
	public P_Project(String name, Key<P_LDUser> owner) {
		this.id = P_AutoIncrement.getNextId(this.getClass());
		this.m_name = name;
		this.m_createdDate = new Date();
		this.m_diagrams = new Vector<Key<P_Diagram>>();
		this.m_owner = owner;
	}

	public Project getShareable() {
		Iterator<Key<P_Diagram>> i = m_diagrams.iterator();
		P_Diagram latest = null;
		while(i.hasNext()) {
			if(latest == null) {
				latest = (P_Diagram) dba.get(i.next());
			}
			P_Diagram thisDiagram = (P_Diagram) dba.get(i.next());
			if(thisDiagram.m_modifiedDate.after(latest.m_modifiedDate))
				latest = thisDiagram;
		}
		Date modified = latest.m_modifiedDate;
		LDUser modifiedBy = latest.m_modifiedBy.getShareable();
		LDUser owner = ((P_LDUser) dba.get(m_owner)).getShareable();
		return new Project(id,
						   m_name, 
						   m_createdDate,
						   owner,
						   modified,
						   modifiedBy);
	}
	
}
