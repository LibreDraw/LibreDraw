package org.libredraw.server.persistence;

import org.libredraw.shared.Diagram;
import org.libredraw.shared.DiagramType;
import org.libredraw.shared.Branch;
import org.libredraw.shared.GenericAccountConnector;
import org.libredraw.shared.LDUser;
import org.libredraw.shared.Project;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.googlecode.objectify.util.DAOBase;


public class DAO extends DAOBase {
	
	static {
		ObjectifyFactory factory = ObjectifyService.factory();
		for(Class<?> currentClass : AutoIncrement.m_table) {
			factory.register(currentClass);
		}
	}
	
	private final Objectify ofy = ObjectifyService.begin();
	
	/**
	 * @param accountConnector
	 * @return P_Key to reference created LDUser
	 * @throws Exception
	 */
	public Key<LDUser> createLDUser(Key<GenericAccountConnector> accountConnector) {
		Key<LDUser> user = ofy.put(new LDUser(AutoIncrement.getNextId(LDUser.class), accountConnector));
		
		//set m_user in accountConnector
		GenericAccountConnector connector = ofy.get(accountConnector);
		connector.m_user = user;
		ofy.put(connector);
		return connector.m_user;
	}
	
	public Key<GenericAccountConnector> createGenericAccountConnector(String email, String password, String diaplayName) {
		return ofy.put(new GenericAccountConnector(AutoIncrement.getNextId(GenericAccountConnector.class), email, password, diaplayName));
	}
	
	public Key<Project> createProject(String name, Key<LDUser> owner) {
		Key<Project> project = ofy.put(new Project(AutoIncrement.getNextId(Project.class), name, owner));
		createAuthorization(owner, project, Permission.OWNER + Permission.ALL);
		return project;
	}
	
	public Key<Diagram> createDiagram(String name, DiagramType type, Key<LDUser> owner) {
		Key<Branch> branch = createBranch("MASTER", owner);
		Key<Diagram> diagram = ofy.put(new Diagram(AutoIncrement.getNextId(Diagram.class), name, type, owner, branch));
		createAuthorization(owner, diagram, Permission.OWNER + Permission.ALL);
		return diagram;
	}
	
	public Key<Branch> createBranch(String name, Key<LDUser> owner) {
		return ofy.put(new Branch(AutoIncrement.getNextId(Branch.class), name, owner));
	}
	
	public Key<Authorization> createAuthorization(Key<LDUser> user, Key<?> regarding, int granted) {
		Key<Permission> permission = createPermission(granted);
		return ofy.put(new Authorization(user, regarding, permission));
	}
	
	public Key<Permission> createPermission(int granted) {
		return ofy.put(new Permission(granted));
	}
	
	public <T> Object get(Key<T> entity) {
		return ofy.get(entity);
	}
	
	public Key<?> put(Object entity) {
		return ofy.put(entity);
	}
	
	public void delete(Key<?> entity) {
		ofy.delete(entity);
	}
	
	public <T> Query<T> getQuery(Class<T> entityType) {
		return ofy.query(entityType);
	}

	public Project getProject(long id) {
		return ofy.get(new Key<Project>(Project.class, id));
	}
		
}

