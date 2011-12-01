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
	
	public String m_name;
	public Date m_createdDate;
	public Vector<Key<P_Diagram>> m_diagrams;
	public Key<P_LDUser> m_owner;
	
	@Id public long id;
	public boolean locked;
	public boolean limited;
	
	public P_Project(String name, Key<P_LDUser> owner) {
		id = P_AutoIncrement.getNextId(this.getClass());
		m_name = name;
		m_createdDate = new Date();
		m_diagrams = new Vector<Key<P_Diagram>>();
		m_owner = owner;
	}

	public Project getShareable() {
		DAO dba = new DAO();
		/*Iterator<Key<P_Diagram>> i = m_diagrams.iterator();
		P_Diagram latest = null;
		while(i.hasNext()) {
			if(latest == null) {
				latest = (P_Diagram) dba.get(i.next());
			}
			P_Diagram thisDiagram = (P_Diagram) dba.get(i.next());
			if(thisDiagram.m_modifiedDate.after(latest.m_modifiedDate))
				latest = thisDiagram;
		}*/
		Date modified = new Date();//latest.m_modifiedDate;
		LDUser modifiedBy = ((P_LDUser) dba.get(m_owner)).getShareable();//latest.m_modifiedBy.getShareable();
		LDUser owner = ((P_LDUser) dba.get(m_owner)).getShareable();
		return new Project(id,
						   m_name, 
						   m_createdDate,
						   owner,
						   modified,
						   modifiedBy);
	}
	
	public void addDiagram(Key<P_Diagram> diagram) {
		m_diagrams.add(diagram);
	}
	
}
