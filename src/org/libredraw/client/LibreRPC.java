package org.libredraw.client;

import java.util.List;

import org.libredraw.shared.Diagram;
import org.libredraw.shared.DiagramType;
import org.libredraw.shared.DiagramEntity;
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
	
	UMLClass getUMLClass(long id);
	
	UMLInterface getUMLInterface(long id);
	
	UMLAssociation getUMLAssociation(long id);
	

	
	
	UMLNode getNode();
	
	Project getProject(long id);

	Diagram getDiagram(long id);
	
	DiagramEntity getObject(Key<?> key);

}
