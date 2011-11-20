package org.libredraw.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.libredraw.client.LibreRPC;
import org.libredraw.server.persistence.DAO;
import org.libredraw.server.persistence.P_Authorization;
import org.libredraw.server.persistence.P_GenericAccountConnector;
import org.libredraw.server.persistence.P_LDUser;
import org.libredraw.server.persistence.P_Project;
import org.libredraw.server.persistence.P_Session;
import org.libredraw.shared.Project;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query;

public class EngineRPC extends RemoteServiceServlet implements LibreRPC {

	private static final Logger log = Logger.getLogger(EngineRPC.class.getName());

	private static final long serialVersionUID = 1024L;
	
	static DAO dba = new DAO();

	@Override
	public String login(String email, String password) {
		Query<P_GenericAccountConnector> query = 
			dba.getQuery(P_GenericAccountConnector.class).filter("m_email =", email);
		
		P_GenericAccountConnector connector = query.get();
		
		if(connector != null) {
			if(connector.checkPassword(password)) {
				String sessionId = Util.sha1(connector.m_displayName + new Date().toString());
				
				dba.put(new P_Session(sessionId, connector.m_user));
				
				return sessionId;
			}
		}
			
		return null;
	}

	@Override
	public String register(String email, String password, String displayName) {
		//check if e-mail is in use
		Query<P_GenericAccountConnector> query = 
			dba.getQuery(P_GenericAccountConnector.class).filter("m_email =", email);
		if(query.get() != null)
			return("email");
		
		//check if displayName is in use
		query = 
			dba.getQuery(P_GenericAccountConnector.class).filter("m_displayName =", displayName);
		if(query.get() != null)
			return("name");
		
		//create user
		Key<P_GenericAccountConnector> connector = 
			dba.createGenericAccountConnector(email, password, displayName);
		dba.createLDUser(connector);
		
		return "Sucsess";
	}

	@Override
	public String login(String authToken) {
		if(authToken == null)
			return null;
		Query<P_Session> query = 
				dba.getQuery(P_Session.class).filter("m_sessionId =", authToken);
		
		P_Session session = query.get();
		//make sure it has not expired
		if(session != null) {
			if(session.checkSession(authToken)) {
				return "true";
			}
		}
		
		return null;
	}

	@Override
	public List<Project> getProjectList(String sessionId) {
		Key<P_LDUser> user = getUser(sessionId);
		if(user!=null) {
			Query<P_Authorization> query = 
					dba.getQuery(P_Authorization.class).filter("m_user =", user);
			query.filter("m_regarding =", P_Project.class);
			List<Project> projects = new ArrayList<Project>();
			Iterator<P_Authorization> i = query.iterator();
			P_Authorization next = i.next();
			while(next!=null) {
				Key<?> get = next.m_regarding;
				P_Project thisProject = (P_Project) dba.get(get);
				Project p = thisProject.getShareable();
				projects.add(p);
				next = i.next();
			}
			return projects;
		}
		return null;
	}
	
	private Key<P_LDUser> getUser(String sessionId) {
		if(sessionId == null)
			return null;
		Query<P_Session> query = 
				dba.getQuery(P_Session.class).filter("m_sessionId =", sessionId);
		P_Session session = query.get();
		log.warning("sent: "+ sessionId + " found: "+session.m_sessionId);
		if(session == null) {
			log.warning("rerurn null");
			return null;
		} else {
			log.warning("return user");
			return session.m_user;
		}
	}

	@Override
	public String createProject(String sessionId, String projectName) {
		Key<P_LDUser> owner = getUser(sessionId);
		if(owner!=null) {
			dba.createProject(projectName, owner);
			return "Sucsess";
		} else
			return null;
	}

}
