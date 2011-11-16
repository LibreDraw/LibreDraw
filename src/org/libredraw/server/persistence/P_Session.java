package org.libredraw.server.persistence;

import java.util.Date;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.libredraw.server.EngineRPC;
import com.googlecode.objectify.Key;

@Entity
public class P_Session {
	
	@Id
	public String m_sessionId;
	public Key<P_LDUser> m_user;
	public Date m_ttl;
	
	private static Logger log = Logger.getLogger(EngineRPC.class.getName());
	
	public P_Session(String sessionId, Key<P_LDUser> user) {
		m_sessionId = sessionId;
		m_user = user;
		m_ttl = new Date(new Date().getTime() + 1000 * 60 * 60 * 24 * 14);
	}

	public boolean checkSession(String sessionId) {
		log.warning("sent: "+sessionId+" stored: "+m_sessionId);
		if(m_sessionId.equals(sessionId)) {
			log.warning("compare true");
			if(m_ttl.after(new Date())) {
			log.warning("date true");
				return true;
			}
		}
		log.warning("compare false");
		return false;
	}
	
}
