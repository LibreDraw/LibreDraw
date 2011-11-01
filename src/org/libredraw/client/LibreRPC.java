package org.libredraw.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("request")
public interface LibreRPC extends RemoteService {

	String login(String email, String password);

	String register(String email, String password, String displayName) throws Exception;

	String login(String authToken);
	
	/*Project[] getProjectList();
	
	Project[] getProject(long projectId);
	
	Version getDiagram(long diagramId, int version);
	
	boolean createDiagram(String name, DiagramType type);
	
	boolean createProject(String name);
	
	String lockEnity(Key entity);
	
	boolean commitEntity(Key entity);*/

}
