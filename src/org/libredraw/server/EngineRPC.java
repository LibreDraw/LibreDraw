/*
	This file is part of LibreDraw.

    LibreDraw is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    LibreDraw is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with LibreDraw.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.libredraw.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Vector;
import org.libredraw.client.LibreRPC;
import org.libredraw.server.persistence.*;
import org.libredraw.shared.*;
import org.libredraw.shared.umlclassdiagram.*;

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
				String sessionId = Util.sha1Java(connector.m_displayName + new Date().toString());
				
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
				if("Project".equals(get.getKind()))
				{
					Project thisProject = (Project) dba.get(get);
					Project p = thisProject;
					projects.add(TransientUpdator.u(p));
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
		//TODO authorizarion check
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
		if(getUser(sessionId) == null)
			return null;
		//TODO authorization check
		List<Diagram> result = new ArrayList<Diagram>();
		Project project = dba.getProject(projectId);
		if(project.m_diagrams!=null && !project.m_diagrams.isEmpty())
			for(Key<Diagram> d : project.m_diagrams) 
				result.add(TransientUpdator.u((Diagram) dba.get(d)));
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String addClass(String sessionId, long branch, UMLClass theClass) {
		Key<LDUser> owner = getUser(sessionId);
		if(owner ==null)
			return null;
		//TODO Authorization check
		theClass.id = AutoIncrement.getNextId(UMLClass.class);
		theClass.m_operations = new Vector<Key<UMLOperation>>();
		for(UMLOperation o : theClass.operations) {
			o.id = AutoIncrement.getNextId(UMLOperation.class);
			theClass.m_operations.add((Key<UMLOperation>) dba.put(o));
		}
		theClass.m_attributes = new Vector<Key<UMLAttribute>>();
		for(UMLAttribute a : theClass.attributes) {
			a.id = AutoIncrement.getNextId(UMLAttribute.class);
			theClass.m_attributes.add((Key<UMLAttribute>) dba.put(a));
		}
		theClass.m_createdBy = owner;
		theClass.m_modifiedBy = owner;
		Key<DiagramEntity> key = (Key<DiagramEntity>) dba.put(theClass);
		
		Key<Version> v = dba.getLatestVersion(new Key<Branch>(Branch.class, branch));
		if(v == null) {
			Version next = new Version(AutoIncrement.getNextId(Version.class), 0, null, owner);
			next.add(key);
			dba.putNewVersion(new Key<Branch>(Branch.class, branch), (Key<Version>) dba.put(next));
			return "Sucsess";
		} else {
			Version next = TransientUpdator.nextVersion(v, owner);
			
			next.add(key);
			
			dba.putNewVersion(new Key<Branch>(Branch.class, branch), (Key<Version>) dba.put(next));
			return "Sucsess";
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String addInterface(String sessionId, long branch, UMLInterface theInterface) {
		Key<LDUser> owner = getUser(sessionId);
		if(owner ==null)
			return null;
		//TODO Authorization check
		theInterface.id = AutoIncrement.getNextId(UMLInterface.class);
		theInterface.m_operations = new Vector<Key<UMLOperation>>();
		for(UMLOperation o : theInterface.operations) {
			o.id = AutoIncrement.getNextId(UMLOperation.class);
			theInterface.m_operations.add((Key<UMLOperation>) dba.put(o));
		}
		theInterface.m_attributes = new Vector<Key<UMLAttribute>>();
		for(UMLAttribute a : theInterface.attributes) {
			a.id = AutoIncrement.getNextId(UMLAttribute.class);
			theInterface.m_attributes.add((Key<UMLAttribute>) dba.put(a));
		}
		theInterface.m_createdBy = owner;
		theInterface.m_modifiedBy = owner;
		Key<DiagramEntity> key = (Key<DiagramEntity>) dba.put(theInterface);
		
		Key<Version> v = dba.getLatestVersion(new Key<Branch>(Branch.class, branch));
		if(v == null) {
			Version next = new Version(AutoIncrement.getNextId(Version.class), 0, null, owner);
			next.add(key);
			dba.putNewVersion(new Key<Branch>(Branch.class, branch), (Key<Version>) dba.put(next));
			return "Sucsess";
		} else {
			Version next = TransientUpdator.nextVersion(v, owner);
			
			next.add(key);
			
			dba.putNewVersion(new Key<Branch>(Branch.class, branch), (Key<Version>) dba.put(next));
			return "Sucsess";
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String addAssocation(String sessionId, long branch, 
			UMLAssociation theAssocation) {
		Key<LDUser> owner = getUser(sessionId);
		if(owner ==null)
			return null;
		theAssocation.id = AutoIncrement.getNextId(UMLAssociation.class);
		theAssocation.m_createdBy = owner;
		theAssocation.m_modifiedBy = owner;
		Key<DiagramEntity> key = (Key<DiagramEntity>) dba.put(theAssocation);
		
		Key<Version> v = dba.getLatestVersion(new Key<Branch>(Branch.class, branch));
		if(v == null) {
			Version next = new Version(AutoIncrement.getNextId(Version.class), 0, null, owner);
			next.add(key);
			dba.putNewVersion(new Key<Branch>(Branch.class, branch), (Key<Version>) dba.put(next));
			return "Sucsess";
		} else {
			Version next = TransientUpdator.nextVersion(v, owner);
			
			next.add(key);
			
			dba.putNewVersion(new Key<Branch>(Branch.class, branch), (Key<Version>) dba.put(next));
			return "Sucsess";
		}
	}
	
	@Override
	public List<DiagramEntity> getEntities(String sessionId, long branch) {
		Key<LDUser> owner = getUser(sessionId);
		if(owner ==null)
			return null;
		Key<Version> v = dba.getLatestVersion(new Key<Branch>(Branch.class, branch));
		if(v == null)
			return null;
		Version ver = (Version) dba.get(v);
		
		List<DiagramEntity> result = new ArrayList<DiagramEntity>();
		for(Key<DiagramEntity> o: ver.m_objects) {
			DiagramEntity e = (DiagramEntity) dba.get(o);
			result.add(TransientUpdator.u(e));
		}
		return result;
	}
	
	@Override
	public boolean lock(String sessionId, Key<?> key) {
		Key<LDUser> owner = getUser(sessionId);
		if(owner ==null)
			return false;
		DiagramEntity d = (DiagramEntity) dba.get(key);
		
		if(d.m_locked = true)
			return false;
		else {
			d.m_locked = true;
			d.m_lockedBy = owner;
			dba.put(d);
			return limitNeighbors(TransientUpdator.u(d), owner);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private boolean limitNeighbors(DiagramEntity d, Key<LDUser> owner) {
		if(d.getClass() == UMLClass.class) {
			Key<UMLClass> key = (Key<UMLClass>) d.entityKey;
			
			if(d.m_locked && d.m_lockedBy == owner) {
				List<UMLAssociation> l = searchAssociations(key);
				if(l == null)
					return true;
				else {
					for(UMLAssociation a: l) 
						if(!limitNeighbors(TransientUpdator.u((DiagramEntity)a), owner))
							return false;
					return true;
				}
			} else {
				d.m_limited = true;
				d.m_lockedBy = owner;
				dba.put(d);
				return true;
			}
		} else if(d.getClass() == UMLInterface.class) {
			Key<UMLInterface> key = (Key<UMLInterface>) d.entityKey;
			
			if(d.m_locked && d.m_lockedBy == owner) {
				List<UMLAssociation> l = searchAssociations(key);
				if(l == null)
					return true;
				else {
					for(UMLAssociation a: l) 
						if(!limitNeighbors(TransientUpdator.u((DiagramEntity)a), owner))
							return false;
					return true;
				}
			} else {
				d.m_limited = true;
				d.m_lockedBy = owner;
				dba.put(d);
				return true;
			}
		} else if(d.getClass() == UMLAssociation.class) {
			UMLAssociation a = (UMLAssociation) dba.get(d.entityKey);
			DiagramEntity left = (DiagramEntity) dba.get(a.m_left);
			DiagramEntity right = (DiagramEntity) dba.get(a.m_left);
			
			if(left.m_locked == false) {
				left.m_limited = true;
				left.m_lockedBy = owner;
				dba.put(left);
			}
			
			if(right.m_locked == false) {
				right.m_limited = true;
				right.m_lockedBy = owner;
				dba.put(right);
			}
			
			a.m_limited = true;
			a.m_lockedBy = owner;
			
			dba.put(a);
			
			return true;
		}
		return false;
	}

	private List<UMLAssociation> searchAssociations(Key<?> d) {
		List<UMLAssociation> result = new ArrayList<UMLAssociation>();
		Query<UMLAssociation> q = dba.getQuery(UMLAssociation.class);
		for(UMLAssociation a : q)
			if(a.m_left == d || a.m_right == d)
				result.add(a);
		return result;
	}
	
	@Override
	public UMLClass getUMLClass(String sessionId, long id) {
		Key<LDUser> owner = getUser(sessionId);
		if(owner ==null)
			return null;
		//TODO permission check
		return TransientUpdator.u((UMLClass) dba.get(new Key<UMLClass>(UMLClass.class, id)));
	}
	
	@Override
	public UMLInterface getUMLInterface(String sessionId, long id) {
		Key<LDUser> owner = getUser(sessionId);
		if(owner ==null)
			return null;
		//TODO permission check
		return TransientUpdator.u((UMLInterface) dba.get(new Key<UMLInterface>(UMLInterface.class, id)));
	}

	@Override
	public UMLAssociation getUMLAssociation(String sessionId, long id) {
		Key<LDUser> owner = getUser(sessionId);
		if(owner ==null)
			return null;
		//TODO permission check
		return TransientUpdator.u((UMLAssociation) dba.get(new Key<UMLAssociation>(UMLAssociation.class, id)));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateUMLClass(String sessionId, long branch,
			UMLClass theClass) {
		Key<LDUser> owner = getUser(sessionId);
		if(owner ==null)
			return false;
		//TODO permission check
		
		
		//TODO check locked
		
		long oldId = theClass.id;

		Key<DiagramEntity> key = (Key<DiagramEntity>) dba.put(TransientUpdator.p(theClass, owner));
		
		Key<Version> v = dba.getLatestVersion(new Key<Branch>(Branch.class, branch));
		if(v == null) {
			return false;
		} else {
			Version next = TransientUpdator.nextVersion(v, owner);
			
			for(Key<DiagramEntity> d : next.m_objects)
				if(d.getId() == oldId) {
					next.m_objects.remove(d);
					break;
				}
			
			next.add(key);
			
			dba.putNewVersion(new Key<Branch>(Branch.class, branch), (Key<Version>) dba.put(next));
			return true;
		}
	}

	@Override
	public boolean updateUMLInterface(String sessionId, long branch,
			UMLInterface theInterface) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUMLAssociation(String sessionId, long branch,
			UMLAssociation theAssociation) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public List<PermissionRecord> getPermissionsForProject(String sessionId, long project) {
		Key<LDUser> owner = getUser(sessionId);
		if(owner ==null)
			return null;
		
		//TODO permission check
		
		Query<Authorization> q = dba.getQuery(Authorization.class);
		
		List<PermissionRecord> result = new ArrayList<PermissionRecord>();
		
		for(Authorization a : q) {
			if(a.m_regarding.getId() == project && "Project".equals(a.m_regarding.getKind())) {
				Permission p = (Permission) dba.get(a.m_granted);
				LDUser u = TransientUpdator.u((LDUser) dba.get(a.m_user));
				result.add(p.getRecord(u));
			}
		}
		return result;
	}

	@Override
	public List<PermissionRecord> getPermissionsForDiagram(String sessionId, long diagram) {
		Key<LDUser> owner = getUser(sessionId);
		if(owner ==null)
			return null;
		
		//TODO permission check
		
		Query<Authorization> q = dba.getQuery(Authorization.class);
		
		List<PermissionRecord> result = new ArrayList<PermissionRecord>();
		
		for(Authorization a : q) {
			if(a.m_regarding.getId() == diagram && "Diagram".equals(a.m_regarding.getKind())) {
				Permission p = (Permission) dba.get(a.m_granted);
				LDUser u = TransientUpdator.u((LDUser) dba.get(a.m_user));
				result.add(p.getRecord(u));
			}
		}
		return result;
	}
	
	@Override
	public boolean putPermissionsProject(String sessionId, long project,
			List<PermissionRecord> permissions) {
		Key<LDUser> owner = getUser(sessionId);
		if(owner ==null)
			return false;
		
		//TODO permission check
		
		Query<Authorization> q = dba.getQuery(Authorization.class);
		
		for(Authorization a : q) {
			if(a.m_regarding.getId() == project && "Project".equals(a.m_regarding.getKind())) {
				Permission p = (Permission) dba.get(a.m_granted);
				for(PermissionRecord r: permissions) {
					if(a.m_user.getId() == r.m_user.id && TransientUpdator.getPermission(r) != p.code) {
						p.code = TransientUpdator.getPermission(r);
					} else if(TransientUpdator.getPermission(r) != p.code) {
						
						dba.createAuthorization(new Key<LDUser>(LDUser.class, r.m_user.id),
								new Key<Project>(Project.class, project), 
								TransientUpdator.getPermission(r));
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean putPermissionsDiagram(String sessionId, long diagram,
			List<PermissionRecord> permissions) {
		Key<LDUser> owner = getUser(sessionId);
		if(owner ==null)
			return false;
		
		//TODO permission check
		
		Query<Authorization> q = dba.getQuery(Authorization.class);
		
		for(Authorization a : q) {
			if(a.m_regarding.getId() == diagram && "Diagram".equals(a.m_regarding.getKind())) {
				Permission p = (Permission) dba.get(a.m_granted);
				for(PermissionRecord r: permissions) {
					if(a.m_user.getId() == r.m_user.id && TransientUpdator.getPermission(r) != p.code) {
						p.code = TransientUpdator.getPermission(r);
					} else if(TransientUpdator.getPermission(r) != p.code) {
						
						dba.createAuthorization(new Key<LDUser>(LDUser.class, r.m_user.id),
								new Key<Diagram>(Diagram.class, diagram), 
								TransientUpdator.getPermission(r));
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean userExists(String sessionId, String displayName) {
		Key<LDUser> owner = getUser(sessionId);
		if(owner ==null)
			return false;
		
		//TODO permission check
		
		Query<GenericAccountConnector> query = dba.getQuery(GenericAccountConnector.class);
		
		query = 
				dba.getQuery(GenericAccountConnector.class).filter("m_displayName =", displayName);
		if(query.get() != null)
			return true;
		return false;
	}
	
	@Override
	public boolean changeNameProject(String sessionId, long project, String name) {
		Key<LDUser> owner = getUser(sessionId);
		if(owner ==null)
			return false;
		
		//TODO permission check
		
		Project thisProject = (Project) dba.get(new Key<Project>(Project.class, project));
		thisProject.m_name = name;
		dba.put(thisProject);
		return true;
	}

	@Override
	public boolean changeNameDiagram(String sessionId, long diagram, String name) {
		Key<LDUser> owner = getUser(sessionId);
		if(owner ==null)
			return false;
		
		//TODO permission check
		
		Diagram thisDiagram = (Diagram) dba.get(new Key<Diagram>(Diagram.class, diagram));
		thisDiagram.m_name = name;
		dba.put(thisDiagram);
		return true;
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

	@Override
	public DiagramEntity getObject(Key<?> key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UMLNode getNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PermissionRecord getPermission(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
