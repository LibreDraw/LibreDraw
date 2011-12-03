package org.libredraw.shared;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.libredraw.shared.Project;
import com.googlecode.objectify.Key;

@Entity
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;
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
	
	public Project(long newId, String name, Key<LDUser> owner) {
		id = newId;
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
}
