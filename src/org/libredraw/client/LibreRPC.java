package org.libredraw.client;

import org.libredraw.shared.DiagramType;
import org.libredraw.shared.Key;
import org.libredraw.shared.Project;
import org.libredraw.shared.Version;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("request")
public interface LibreRPC extends RemoteService {

	String login(String email, String password);

	String register(String email, String password, String displayName);

	String login(String authToken);
	
	Project[] getProjectList();
	
	Project[] getProject(long projectId);
	
	Version getDiagram(long diagramId, int version);
	
	boolean createDiagram(String name, DiagramType type);
	
	boolean createProject(String name);
	
	String lockEnity(Key entity);
	
	boolean commitEntity(Key entity);

}
