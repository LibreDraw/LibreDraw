package org.libredraw.server;

import java.util.Date;

import org.libredraw.client.LibreRPC;
import org.libredraw.server.persistence.DAO;
import org.libredraw.server.persistence.P_GenericAccountConnector;
import org.libredraw.server.persistence.P_Session;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query;

public class EngineRPC extends RemoteServiceServlet implements LibreRPC {
	

	private static final long serialVersionUID = 1024L;
	
	static DAO dba = new DAO();

	@Override
	public String login(String email, String password) {
		Query<P_GenericAccountConnector> query = 
			dba.getQuery(P_GenericAccountConnector.class).filter("m_email =", email);
		
		P_GenericAccountConnector connector = query.get();
		
		if(connector != null && connector.checkPassword(password)) {
			String sessionId = Util.sha1(connector.m_displayName + new Date().toString());
			
			dba.put(new P_Session(sessionId, connector.m_user));
			
			return sessionId;
		}
			
		return null;
	}

	@Override
	public String register(String email, String password, String displayName) {
		Query<P_GenericAccountConnector> query = 
			dba.getQuery(P_GenericAccountConnector.class).filter("m_email =", email);

		if(query.get() != null)
			return("email");
		
		query = 
			dba.getQuery(P_GenericAccountConnector.class).filter("m_displayName =", displayName);
		
		if(query.get() != null)
			return("name");
		
		Key<P_GenericAccountConnector> connector = 
			dba.createGenericAccountConnector(email, password, displayName);
		dba.createLDUser(connector);
		
		return "Sucsess";
	}

	@Override
	public String login(String authToken) {
		Query<P_Session> query = 
				dba.getQuery(P_Session.class).filter("m_sessionId =", authToken);
		
		P_Session session = query.get();
		
		if(session != null) {
			return session.m_sessionId; 
		}
		
		return null;
	}

}
