package org.libredraw.shared;

import java.io.Serializable;

public class LDUser implements Serializable {
	
	public String m_displayName;
	
	public LDUser() {
	}
	
	public LDUser(String name) {
		m_displayName = name;
	}
}
