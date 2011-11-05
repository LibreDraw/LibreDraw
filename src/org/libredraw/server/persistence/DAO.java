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
	public Key<?> createLDUser(Key<?> accountConnector) {
		return ofy.put(new P_LDUser(accountConnector));
	}
	
	public Key<?> createGenericAccountConnector(String email, String password, String diaplayName) {
		return ofy.put(new P_GenericAccountConnector(email, password, diaplayName));
	}
	
	public Key<?> createProject(String name, Key<?> owner) {
		return ofy.put(new P_Project(name, owner));
	}
	
	public Key<?> createAuthorization(Key<?> user, Key<?> regarding, P_Permission granted) {
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

