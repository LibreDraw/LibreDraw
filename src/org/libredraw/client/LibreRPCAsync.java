package org.libredraw.client;

import java.util.List;

import org.libredraw.shared.Project;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LibreRPCAsync {
	void login(String email, String password, AsyncCallback<String> callback);
	
	void login(String authToken, AsyncCallback<String> callback);
	
	void register(String email, String password, String displayName, AsyncCallback<String> callback);

	void getProjectList(String sessionId, AsyncCallback<List<Project>> callback);

	void createProject(String sessionId, String projectName,
			AsyncCallback<String> callback);
	
	/*void commitEntity(Key entity, AsyncCallback<Boolean> callback);
	
	void createDiagram(String name, DiagramType type,
			AsyncCallback<Boolean> callback);
	
	void getDiagram(long diagramId, int version, AsyncCallback<Version> callback);
	
	void getProject(long projectId, AsyncCallback<Project[]> callback);
	
	void getProjectList(AsyncCallback<Project[]> callback);
	
	void lockEnity(Key entity, AsyncCallback<String> callback);*/
	
}
