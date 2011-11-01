package org.libredraw.server;

import org.libredraw.client.LibreRPC;
import org.libredraw.server.persistence.DAO;
import org.libredraw.server.persistence.P_GenericAccountConnector;
import org.libredraw.server.persistence.P_LDUser;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Query;

public class EngineRPC extends RemoteServiceServlet implements LibreRPC {
	
	static DAO dba = new DAO();

	@Override
	public String login(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String register(String email, String password, String displayName) throws Exception {
		Query<P_GenericAccountConnector> query = 
			dba.getQuery(P_GenericAccountConnector.class).filter("m_email =", email);

		if(query.get() != null)
			throw new Exception("email in use");
		
		try {
			dba.createLDUser(dba.createGenericAccountConnector(email, password, displayName));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); //This should never happen!
		}
		return "Sucsess";
	}

	@Override
	public String login(String authToken) {
		// TODO Auto-generated method stub
		return null;
	}

}
