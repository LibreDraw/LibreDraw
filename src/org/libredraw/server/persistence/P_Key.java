package org.libredraw.server.persistence;

import java.io.Serializable;

public class P_Key {
	public long id;
	public Class<?> entityType;
	
	public P_Key(Class<?> type, long id) {
		this.entityType = type;
		this.id = id;
	}
}
