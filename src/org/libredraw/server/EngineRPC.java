package org.libredraw.server;

import org.libredraw.client.LibreRPC;
import org.libredraw.shared.Project;
import org.libredraw.shared.Version;

import com.google.appengine.api.datastore.Key;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class EngineRPC extends RemoteServiceServlet implements LibreRPC {

	@Override
	public String login(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String register(String email, String password, String displayName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String login(String authToken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Project[] getProjectList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Project[] getProject(long projectId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Version getDiagram(long diagramId, int version) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createDiagram(String name, Object type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createProject(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String lockEnity(Object diagram, Key entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean commitEntity(Object entity, Object id) {
		// TODO Auto-generated method stub
		return false;
	}

}
