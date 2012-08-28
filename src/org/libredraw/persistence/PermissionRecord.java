package org.libredraw.persistence;

import java.io.Serializable;

public class PermissionRecord  implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public LDUser m_user;
	
	public boolean READ;
	public boolean WRITE;
	public boolean BRANCH;
	public boolean MERGE;
	public boolean EXPORT;
	public boolean OWNER;
	
	public PermissionRecord() {
		
	}
	
	public PermissionRecord(LDUser u) {
		m_user = u;
	}
}
