package org.libredraw.client;

import java.util.List;

import org.libredraw.shared.Diagram;
import org.libredraw.shared.DiagramType;
import org.libredraw.shared.Project;
import org.libredraw.shared.umlclassdiagram.UMLClass;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("request")
public interface LibreRPC extends RemoteService {

	String login(String email, String password);

	String register(String email, String password, String displayName);

	String login(String sessionId);
	
	String endSession(String sessionId);
	
	List<Project> getProjectList(String sessionId);
	
	String createProject(String sessionId, String projectName);
	
	String createDiagram(String sessionId, long projectId, String diagramName, DiagramType type);
	
	List<Diagram> getDiagramList(String sessionId, long projectId);
	
	String addClass(String sessionId, long branch, UMLClass theClass);
	
	/*Project[] getProject(long projectId);
	
	Version getDiagram(long diagramId, int version);
	
	boolean createDiagram(String name, DiagramType type);
	
	boolean createProject(String name);
	
	String lockEnity(Key entity);
	
	boolean commitEntity(Key entity);*/
	
	Project getProject(long id);

	Diagram getDiagram(long id);

}
