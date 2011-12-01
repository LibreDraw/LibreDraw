package org.libredraw.server.persistence;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.libredraw.shared.LDUser;


import com.googlecode.objectify.Key;

@Entity
public class Session {

	@Id public long id;
	public String m_sessionId;
	public Key<LDUser> m_user;
	public Date m_ttl;
	
	public Session() {
		
	}

	public Session(String sessionId, Key<LDUser> user) {
		id = AutoIncrement.getNextId(this.getClass());
		m_sessionId = sessionId;
		m_user = user;
		m_ttl = new Date(new Date().getTime() + 1000 * 60 * 60 * 24 * 14);
	}

	public boolean checkSession(String sessionId) {
		if(m_sessionId.equals(sessionId)) {
			if(m_ttl.after(new Date())) {
				return true;
			}
		}
		return false;
	}

}
