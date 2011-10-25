package org.libredraw.client;

import org.libredraw.shared.Project;
import org.libredraw.shared.Version;
import com.google.appengine.api.datastore.Key;
import com.google.gwt.user.client.rpc.RemoteService;

public interface LibreRPC extends RemoteService {

	String login(String email, String password);

	String register(String email, String password, String displayName);

	String login(String authToken);
	
	Project[] getProjectList();
	
	Project[] getProject(long projectId);
	
	Version getDiagram(long diagramId, int version);
	
	boolean createDiagram(String name, Object type);
	
	boolean createProject(String name);
	
	String lockEnity(Object diagram, Key entity);
	
	boolean commitEntity(Object entity, Object id);

}
