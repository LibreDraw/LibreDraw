package org.libredraw.shared;

import java.util.Date;
import java.util.Vector;

import javax.persistence.Id;

import com.googlecode.objectify.Key;

public class Version {
	public Version() {
		// TODO Auto-generated constructor stub
	}
	
	@Id public long id;
	public boolean locked;
	public boolean limited;
	
	public String m_tag;
	public int m_versionNuber;
	public Date m_date;
	public Vector<Key<?>> m_diagram;
	public Key<?> m_previousVersion;
	public Key<?> m_modifiedBy;
}
