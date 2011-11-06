package org.libredraw.server;

import org.libredraw.client.LibreRPC;
import org.libredraw.server.persistence.DAO;
import org.libredraw.server.persistence.P_GenericAccountConnector;
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
		
		P_GenericAccountConnector account = query.get();
		
		if(account != null && account.checkPassword(password)) {
			return "Sucsess";
		}
			
		return "bad combination";
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
		
		Key<?> connector = 
			dba.createGenericAccountConnector(email, password, displayName);
		dba.createLDUser(connector);
		
		return "Sucsess";
	}

	@Override
	public String login(String authToken) {
		// TODO Auto-generated method stub
		return null;
	}

}
