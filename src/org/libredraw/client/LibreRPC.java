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

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.googlecode.objectify.Key;

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
	
	String addInterface(String sessionId, long branch, UMLInterface theInterface);
	
	String addAssocation(String sessionId, long branch, UMLAssociation theAssocation);
	
	List<DiagramEntity> getEntities(String sessionId, long branch);
	
	boolean lock(String sessionId, Key<?> key);
	
	UMLClass getUMLClass(String sessionId, long id);
	
	UMLInterface getUMLInterface(String sessionId, long id);
	
	UMLAssociation getUMLAssociation(String sessionId, long id);
	
	boolean updateUMLClass(String sessionId, long branch, UMLClass theClass);
	
	boolean updateUMLInterface(String sessionId, long branch, UMLInterface theInterface);
	
	boolean updateUMLAssociation(String sessionId, long branch, UMLAssociation theAssociation);
	
	List<PermissionRecord> getPermissionsForProject(String sessionId, long project);
	
	List<PermissionRecord> getPermissionsForDiagram(String sessionId, long diagram);
	
	boolean putPermissionsProject(String sessionId, long project, List<PermissionRecord> permissions);
	
	boolean putPermissionsDiagram(String sessionId, long diagram, List<PermissionRecord> permissions);
	
	LDUser userExists(String sessionId, String text);
	
	boolean changeNameProject(String sessionId, long project, String name);
	
	boolean changeNameDiagram(String sessionId, long diagram, String name);
	
	boolean unlock(String sessionId, Key<?> key);
	

	
	
	UMLNode getNode();
	
	Project getProject(long id);

	Diagram getDiagram(long id);
	
	DiagramEntity getObject(Key<?> key);
	
	PermissionRecord getPermission(long id);

}
