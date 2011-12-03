package org.libredraw.client;

import java.util.List;

import org.libredraw.shared.Diagram;
import org.libredraw.shared.DiagramType;
import org.libredraw.shared.DiagramEntity;
import org.libredraw.shared.Project;
import org.libredraw.shared.umlclassdiagram.UMLClass;
import org.libredraw.shared.umlclassdiagram.UMLNode;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.objectify.Key;

public interface LibreRPCAsync {
	void login(String email, String password, AsyncCallback<String> callback);
	
	void login(String sessionId, AsyncCallback<String> callback);
	
	void register(String email, String password, String displayName, AsyncCallback<String> callback);

	void getProjectList(String sessionId, AsyncCallback<List<Project>> callback);

	void createProject(String sessionId, String projectName,
			AsyncCallback<String> callback);

	void endSession(String sessionId, AsyncCallback<String> callback);

	void createDiagram(String sessionId, long projectId, String diagramName,
			DiagramType type, AsyncCallback<String> callback);

	void getDiagramList(String sessionId, long projectId,
			AsyncCallback<List<Diagram>> callback);

	void addClass(String sessionId, long branch, UMLClass theClass,
			AsyncCallback<String> callback);

	void getEntities(String sessionId, long branch,
			AsyncCallback<List<DiagramEntity>> callback);
	
	
	
	void getDiagram(long id, AsyncCallback<Diagram> callback);
	
	void getProject(long id, AsyncCallback<Project> callback);

	void getObject(Key<?> key, AsyncCallback<DiagramEntity> callback);

	void getUMLClass(AsyncCallback<UMLClass> callback);

	void getNode(AsyncCallback<UMLNode> callback);
	
}
