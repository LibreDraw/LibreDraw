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


package org.libredraw.client;

import java.util.List;

import org.libredraw.shared.Diagram;
import org.libredraw.shared.DiagramType;
import org.libredraw.shared.DiagramEntity;
import org.libredraw.shared.LDUser;
import org.libredraw.shared.PermissionRecord;
import org.libredraw.shared.Project;
import org.libredraw.shared.umlclassdiagram.UMLAssociation;
import org.libredraw.shared.umlclassdiagram.UMLClass;
import org.libredraw.shared.umlclassdiagram.UMLInterface;
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
	
	void addInterface(String sessionId, long branch,
			UMLInterface theInterface, AsyncCallback<String> asyncCallback);
	
	void addAssocation(String sessionId, long branch,
			UMLAssociation theAssocation, AsyncCallback<String> callback);

	void getEntities(String sessionId, long branch,
			AsyncCallback<List<DiagramEntity>> callback);
	
	void lock(String sessionId, Key<?> key, AsyncCallback<Boolean> callback);
	
	void getUMLClass(String sessionId, long id, AsyncCallback<UMLClass> callback);
	
	void getUMLInterface(String sessionId, long id,
			AsyncCallback<UMLInterface> callback);

	void getUMLAssociation(String sessionId, long id,
			AsyncCallback<UMLAssociation> callback);
	
	void updateUMLClass(String sessionId, long branch, UMLClass theClass,
			AsyncCallback<Boolean> callback);

	void updateUMLInterface(String sessionId, long branch,
			UMLInterface theInterface, AsyncCallback<Boolean> callback);

	void updateUMLAssociation(String sessionId, long branch,
			UMLAssociation theAssociation, AsyncCallback<Boolean> callback);
	
	void getPermissionsForProject(String sessionId, long project,
			AsyncCallback<List<PermissionRecord>> callback);

	void getPermissionsForDiagram(String sessionId, long diagram,
			AsyncCallback<List<PermissionRecord>> callback);

	void putPermissionsProject(String sessionId, long project,
			List<PermissionRecord> permissions, AsyncCallback<Boolean> callback);

	void putPermissionsDiagram(String sessionId, long diagram,
			List<PermissionRecord> permissions, AsyncCallback<Boolean> callback);

	void userExists(String sessionId, String text,
			AsyncCallback<LDUser> asyncCallback);
	
	void changeNameProject(String sessionId, long project, String name,
			AsyncCallback<Boolean> callback);

	void changeNameDiagram(String sessionId, long diagram, String name,
			AsyncCallback<Boolean> callback);
	
	void unlock(String sessionId, Key<?> key, AsyncCallback<Boolean> callback);
	
	
	
	
	
	void getDiagram(long id, AsyncCallback<Diagram> callback);
	
	void getProject(long id, AsyncCallback<Project> callback);

	void getObject(Key<?> key, AsyncCallback<DiagramEntity> callback);

	void getNode(AsyncCallback<UMLNode> callback);
	
	void getPermission(long id, AsyncCallback<PermissionRecord> callback);



}
