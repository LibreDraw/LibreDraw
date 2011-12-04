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
