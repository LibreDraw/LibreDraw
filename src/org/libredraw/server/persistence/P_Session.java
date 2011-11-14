package org.libredraw.server.persistence;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import com.googlecode.objectify.Key;

@Entity
public class P_Session {
	
	@Id
	public String m_sessionId;
	public Key<P_LDUser> m_user;
	public Date m_ttl;
	
	public P_Session(String sessionId, Key<P_LDUser> user) {
		m_sessionId = sessionId;
		m_user = user;
		m_ttl = new Date(new Date().getTime() + 1000 * 60 * 60 * 24 * 14);
	}
	
}
