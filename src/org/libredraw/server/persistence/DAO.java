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

package org.libredraw.server.persistence;

import java.util.Date;
import java.util.Vector;

import org.libredraw.shared.Diagram;
import org.libredraw.shared.DiagramType;
import org.libredraw.shared.Branch;
import org.libredraw.shared.GenericAccountConnector;
import org.libredraw.shared.LDUser;
import org.libredraw.shared.Project;
import org.libredraw.shared.Version;

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
	
	public Key<Version> getLatestVersion(Key<Branch> k) {
		Branch b = ofy.get(k);
		if(b.m_versions == null)
			return null;
		else {
			Date latest = null;
			Key<Version> latestV = null;
			for(Key<Version> vKey: b.m_versions) {
				Version v = ofy.get(vKey);
				if(latest == null) {
					latest = v.m_date;
					latestV = vKey;
				} else if (latest.before(v.m_date)) {
					latest = v.m_date;
					latestV = vKey;
				}		
			}
			return latestV;
		}
	}
	
	public void putNewVersion(Key<Branch> k, Key<Version> vKey) {
		Branch b = ofy.get(k);
		if(b.m_versions == null) {
			b.m_versions = new Vector<Key<Version>>();
			b.m_versions.add(vKey);
		}
		else
			b.m_versions.add(vKey);
		ofy.put(b);
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

