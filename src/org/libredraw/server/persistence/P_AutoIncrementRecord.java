package org.libredraw.server.persistence;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class P_AutoIncrementRecord {

	@Id public long m_entiyType;
	private long m_nextId;
	
	public P_AutoIncrementRecord(long entity) {
		m_entiyType = entity;
		m_nextId = 1;
	}
	
	public P_AutoIncrementRecord() {
	}
	
	public long getNextId() {
		return m_nextId++;
	}
	
}
