package org.libredraw.server.persistence;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.googlecode.objectify.util.DAOBase;


public class DAO extends DAOBase {
	
	static {
		ObjectifyFactory factory = ObjectifyService.factory();
		for(Class<?> currentClass : P_AutoIncrement.m_table) {
			factory.register(currentClass);
		}
	}
	
	private final Objectify ofy = ObjectifyService.begin();
	
	/**
	 * @param accountConnector
	 * @return P_Key to reference created LDUser
	 * @throws Exception
	 */
	public Key<P_LDUser> createLDUser(Key<P_GenericAccountConnector> accountConnector) {
		Key<P_LDUser> user = ofy.put(new P_LDUser(accountConnector));
		
		//set m_user in accountConnector
		P_GenericAccountConnector connector = ofy.get(accountConnector);
		connector.m_user = user;
		ofy.put(connector);
		return connector.m_user;
	}
	
	public Key<P_GenericAccountConnector> createGenericAccountConnector(String email, String password, String diaplayName) {
		return ofy.put(new P_GenericAccountConnector(email, password, diaplayName));
	}
	
	public Key<P_Project> createProject(String name, Key<P_LDUser> owner) {
		Key<P_Project> project = ofy.put(new P_Project(name, owner));
		createAuthorization(owner, project, P_Permission.OWNER);
		return project;
	}
	
	public Key<P_Authorization> createAuthorization(Key<P_LDUser> user, Key<?> regarding, int granted) {
		return ofy.put(new P_Authorization(user, regarding, granted));
	}
	
	public Object get(Key<?> entity) {
		return ofy.get(entity);
	}
	
	public Key<?> put(Object entity) {
		return ofy.put(entity);
	}
	
	public <T> Query<T> getQuery(Class<T> entityType) {
		return ofy.query(entityType);
	}
		
}

