package org.libredraw.server;

import java.io.Console;

import org.libredraw.client.LibreRPC;
import org.libredraw.server.persistence.DAO;
import org.libredraw.server.persistence.P_GenericAccountConnector;
import org.libredraw.server.persistence.P_Key;

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
		//Query<P_GenericAccountConnector> query = 
		//	dba.getQuery(P_GenericAccountConnector.class).filter("m_email =", email);

		//if(query.get() != null)
			//throw new Exception("email in use");
		
		try {
			P_Key connector = dba.createGenericAccountConnector(email, password, displayName);
			dba.createLDUser(connector);
		} catch (Exception e) {
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
