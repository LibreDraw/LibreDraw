package org.libredraw.server.persistence;

public class P_Key {
	public long id;
	public Class<?> entityType;
	
	public P_Key(Class<?> type, long id) {
		this.entityType = type;
		this.id = id;
	}
}
