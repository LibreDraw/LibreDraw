package org.libredraw.server.persistence;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;
import java.io.UnsupportedEncodingException; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 

public class DAO extends DAOBase {
	
	private static Objectify ofy = ObjectifyService.begin();
	
	public P_Key createLDUser(P_Key accountConnector) throws Exception {
		Key<?> object = ofy.put(new P_LDUser(accountConnector));
		return new P_Key(object.getClass(), object.getId());
	}
	
	public P_Key createGenericAccountConnector(String email, String password, String name) throws Exception {
		Key<?> object = ofy.put(new P_GenericAccountConnector(email, password, name));
		return new P_Key(object.getClass(), object.getId());
	}
	
	public P_Key createProject(String name, P_Key owner) throws Exception {
		Key<?> object = ofy.put(new P_Project(name, owner));
		return new P_Key(object.getClass(), object.getId());
	}
	
}

