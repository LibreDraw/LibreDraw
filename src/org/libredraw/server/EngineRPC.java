package org.libredraw.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.libredraw.client.LibreRPC;
import org.libredraw.server.persistence.DAO;
import org.libredraw.server.persistence.Authorization;
import org.libredraw.server.persistence.GenericAccountConnector;
import org.libredraw.server.persistence.Session;
import org.libredraw.shared.Diagram;
import org.libredraw.shared.DiagramType;
import org.libredraw.shared.LDUser;
import org.libredraw.shared.Project;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query;

public class EngineRPC extends RemoteServiceServlet implements LibreRPC {

	//private static final Logger log = Logger.getLogger(EngineRPC.class.getName());

	private static final long serialVersionUID = 1024L;
	
	static DAO dba = new DAO();

	@Override
	public String login(String email, String password) {
		Query<GenericAccountConnector> query = 
			dba.getQuery(GenericAccountConnector.class).filter("m_email =", email);
		
		GenericAccountConnector connector = query.get();
		
		if(connector != null) {
			if(connector.checkPassword(password)) {
				String sessionId = Util.sha1(connector.m_displayName + new Date().toString());
				
				dba.put(new Session(sessionId, connector.m_user));
				
				return sessionId;
			}
		}
		return null;
	}

	@Override
	public String register(String email, String password, String displayName) {
		//check if e-mail is in use
		Query<GenericAccountConnector> query = 
			dba.getQuery(GenericAccountConnector.class).filter("m_email =", email);
		if(query.get() != null)
			return("email");
		
		//check if displayName is in use
		query = 
			dba.getQuery(GenericAccountConnector.class).filter("m_displayName =", displayName);
		if(query.get() != null)
			return("name");
		
		//create user
		Key<GenericAccountConnector> connector = 
			dba.createGenericAccountConnector(email, password, displayName);
		dba.createLDUser(connector);
		
		return "Sucsess";
	}

	@Override
	public String login(String sessionId) {
		if(sessionId == null)
			return null;
		Query<Session> query = 
				dba.getQuery(Session.class).filter("m_sessionId =", sessionId);
		
		Session session = query.get();
		//make sure it has not expired
		if(session != null) {
			if(session.checkSession(sessionId)) {
				return "true";
			}
		}
		
		return null;
	}

	@Override
	public List<Project> getProjectList(String sessionId) {
		Key<LDUser> user = getUser(sessionId);
		if(user!=null) {
			Query<Authorization> query = 
					dba.getQuery(Authorization.class).filter("m_user =", user);
			List<Project> projects = new ArrayList<Project>();
			Iterator<Authorization> i = query.iterator();
			Authorization next;
			try {
				next = i.next();
			} catch (NoSuchElementException e) {
				next = null;
			}
			while(next!=null) {
				Key<?> get = next.m_regarding;
				if("P_Project".equals(get.getKind()))
				{
					Project thisProject = (Project) dba.get(get);
					Project p = thisProject;
					p.update();
					projects.add(p);
				}
				try {
					next = i.next();
				} catch (NoSuchElementException e) {
					next = null;
				}
			}
			return projects;
		}
		return null;
	}
	
	private Key<LDUser> getUser(String sessionId) {
		if(sessionId == null)
			return null;
		Query<Session> query = 
				dba.getQuery(Session.class).filter("m_sessionId =", sessionId);
		Session session = query.get();
		if(session == null)
			return null;
		else if(session.checkSession(sessionId))
			return session.m_user;
		else
			return null;
	}

	public String createProject(String sessionId, String projectName) {
		Key<LDUser> owner = getUser(sessionId);
		if(owner!=null) {
			dba.createProject(projectName, owner);
			return "Sucsess";
		} else
			return null;
	}

	@Override
	public String endSession(String sessionId) {
		if(sessionId == null)
			return null;
		Query<Session> query = 
				dba.getQuery(Session.class).filter("m_sessionId =", sessionId);
		
		Key<Session> session = query.getKey();
		//make sure it has not expired
		if(session != null) {
			dba.delete(session);
			return "true";
		}
		
		return null;
	}



	@Override
	public String createDiagram(String sessionId, long projectId,
			String DiagramName, DiagramType type) {
		Key<LDUser> owner = getUser(sessionId);
		if(owner!=null) {
			Key<Diagram> diagram = dba.createDiagram(DiagramName, type, owner);
			Project project = dba.getProject(projectId);
			project.addDiagram(diagram);
			dba.put(project);
			return "Sucsess";
		} else
			return null;
	}

	@Override
	public List<Diagram> getDiagramList(String sessionId, long projectId) {
		if(sessionId == null)
			return null;
		else if(getUser(sessionId) == null)
			return null;
		Project project = dba.getProject(projectId);
		Iterator<Key<Diagram>> i = project.m_diagrams.iterator();
		List<Diagram> result = new ArrayList<Diagram>();
		while(i.hasNext()) {
			Key<Diagram> thisDiagram = i.next();
			Diagram d = ((Diagram) dba.get(thisDiagram));
			d.update();
			result.add(d);
		}
		return result;
	}
	
	
	//The following exist only so that the RPC knows that the types are on the 
	//serializable whitelist.
	@Override
	public Project getProject(long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Diagram getDiagram(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
