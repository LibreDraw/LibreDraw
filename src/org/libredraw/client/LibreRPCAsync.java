package org.libredraw.client;

import org.libredraw.shared.Project;
import org.libredraw.shared.Version;
import com.google.appengine.api.datastore.Key;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LibreRPCAsync {
	void login(String email, String password, AsyncCallback<String> callback);
	void login(String authToken, AsyncCallback<String> callback);
	void register(String email, String password, String displayName, AsyncCallback<String> callback);
	void commitEntity(Object entity, Object id, AsyncCallback<Boolean> callback);
	void createDiagram(String name, Object type, AsyncCallback<Boolean> callback);
	void createProject(String name, AsyncCallback<Boolean> callback);
	void getDiagram(long diagramId, int version, AsyncCallback<Version> callback);
	void getProject(long projectId, AsyncCallback<Project[]> callback);
	void getProjectList(AsyncCallback<Project[]> callback);
	void lockEnity(Object diagram, Key entity, AsyncCallback<String> callback);
	
}
