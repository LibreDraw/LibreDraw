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

package org.libredraw.persistence.umlclassdiagram;

import java.io.Serializable;

import org.libredraw.persistence.DiagramEntity;
import org.libredraw.persistence.LDUser;

import com.googlecode.objectify.Key;

public class UMLNode extends DiagramEntity implements Serializable {
	private static final long serialVersionUID = 1481851909631311579L;
	public UMLVisibility m_visibility;
	
	public UMLNode() {
		
	}
	
	public UMLNode(String name, UMLVisibility visibility, Key<LDUser> createdBy) {
		super(name, createdBy);
		m_visibility = visibility;
	}
}
