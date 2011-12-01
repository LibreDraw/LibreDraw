package org.libredraw.shared;

import java.util.Date;
import java.util.Vector;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.libredraw.server.persistence.AutoIncrement;
import org.libredraw.server.persistence.DAO;
import org.libredraw.shared.Project;
import com.googlecode.objectify.Key;

@Entity
public class Project {
	
	@Id public long id;
	public boolean locked;
	public boolean limited;
	
	public String m_name;
	public Date m_createdDate;
	transient public Vector<Key<Diagram>> m_diagrams;
	transient public Key<LDUser> m_owner;
	
	@Transient public LDUser owner;
	@Transient public Date modified;
	@Transient public LDUser modifiedBy;
	

	
	public Project() {
		
	}
	
	public Project(String name, Key<LDUser> owner) {
		id = AutoIncrement.getNextId(this.getClass());
		m_name = name;
		m_createdDate = new Date();
		m_diagrams = new Vector<Key<Diagram>>();
		m_owner = owner;
	}
	
	public void addDiagram(Key<Diagram> diagram) {
		if(m_diagrams == null)
			m_diagrams = new Vector<Key<Diagram>>();
		m_diagrams.add(diagram);
	}
	
	public void update() {
		DAO dba = new DAO();
		owner = (LDUser) dba.get(m_owner);
		if(m_diagrams == null || m_diagrams.isEmpty()) {
			modified = m_createdDate;
			modifiedBy = owner;
		}
		else {
			Date latest = null;
			Diagram latestD = null;
			for(Key<Diagram> k : m_diagrams) {
				Diagram d = (Diagram) dba.get(k);
				Date dDate = d.getModifiedDate();
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
			modified = latest;
			modifiedBy = latestD.getModifiedBy();
		}
			
	}
	
}
