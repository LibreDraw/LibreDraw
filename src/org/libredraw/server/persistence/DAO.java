package org.libredraw.server.persistence;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.googlecode.objectify.util.DAOBase;

public class DAO extends DAOBase {
	
	private static Objectify ofy = ObjectifyService.begin();
	
	/**
	 * @param accountConnector
	 * @return P_Key to reference created LDUser
	 * @throws Exception
	 */
	public P_Key createLDUser(P_Key accountConnector) throws Exception {
		Key<?> object = ofy.put(new P_LDUser(accountConnector));
		return new P_Key(object.getClass(), object.getId());
	}
	
	public P_Key createGenericAccountConnector(String email, String password, String diaplayName) throws Exception {
		Key<?> object = ofy.put(new P_GenericAccountConnector(email, password, diaplayName));
		return new P_Key(object.getClass(), object.getId());
	}
	
	public P_Key createProject(String name, P_Key owner) throws Exception {
		Key<?> object = ofy.put(new P_Project(name, owner));
		return new P_Key(object.getClass(), object.getId());
	}
	
	public P_Key createAuthorization(P_Key user, P_Key regarding, P_Permission granted) throws Exception {
		Key<?> object = ofy.put(new P_Authorization(user, regarding, granted));
		return new P_Key(object.getClass(), object.getId());
	}
	
	public Object get(P_Key entity) {
		return ofy.get(new Key(entity.entityType, entity.id));
	}
	
	public P_Key put(Object entity) {
		Key<?> key = ofy.put(entity);
		return new P_Key(key.getClass(), key.getId());
	}
	
	public <T> Query<T> getQuery(Class<T> entityType) {
		return ofy.query(entityType);
	}
	
}

