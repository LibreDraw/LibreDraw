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

package org.libredraw.shared.umlclassdiagram;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.libredraw.shared.DiagramEntity;
import org.libredraw.shared.LDUser;
import com.googlecode.objectify.Key;

@Entity
public class UMLAssociation extends DiagramEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	public long id;
	
	public String m_leftName;
	public String m_rightName;
	public String m_leftMultiplicity;
	public String m_rightMultiplicity;
	public UMLAssociationType m_type;
	public Key<DiagramEntity> m_left;
	public Key<DiagramEntity> m_right;
	
	@Transient
	public DiagramEntity left;
	@Transient
	public DiagramEntity right;
	
	public UMLAssociation() {
		
	}
	
	public UMLAssociation(String name, Key<DiagramEntity> one, Key<DiagramEntity>two, String leftName, String leftMultiplicity, String rightName, String rightMultiplicity, UMLAssociationType type,  Key<LDUser> createdBy) {
		super(name, createdBy);
		//m_nodes = new Key<DiagramEntity>[2];
		m_left = one;
		m_right = two;
		m_leftName = leftName;
		m_rightName = rightName;
		m_leftMultiplicity = leftMultiplicity;
		m_rightMultiplicity = rightMultiplicity;
		m_type = type;
	}
	
}
