package org.libredraw.server.persistence;

import java.util.Date;
import java.util.Vector;
import javax.persistence.Entity;
import javax.persistence.Id;
import com.googlecode.objectify.Key;

@Entity
public class P_Project {

	public String m_name;
	public Date m_createdDate;
	public Vector<Key<?>> m_diagrams;
	public Key<?> m_owner;
	
	@Id public long id;
	public boolean locked;
	public boolean limited;
	
	public P_Project(String name, Key<?> owner) {
		this.m_name = name;
		this.m_createdDate = new Date();
		this.m_diagrams = new Vector<Key<?>>();
		this.m_owner = owner;
	}
	
}
