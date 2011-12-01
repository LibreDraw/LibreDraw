package org.libredraw.shared;

public enum DiagramType {
	UMLClassDiagram(1),
	UMLUseCaseDiagram(2),
	EntityRelationshipDiagram(3);
	
	private int code;

	private DiagramType(int c) {
		code = c;
	}

	public int getCode() {
		return code;
	}
	
	public String toString() {
		if(this.code == 1)
			return "UML Class";
		if(this.code == 2)
			return "UML Use Case";
		if(this.code == 3)
			return "Entity-Relationship";
		return null;
	}
}
