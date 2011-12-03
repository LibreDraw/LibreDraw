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

import javax.persistence.Entity;
import javax.persistence.Id;

import org.libredraw.shared.DiagramEntity;
import org.libredraw.shared.LDUser;

import com.googlecode.objectify.Key;

@Entity
public class UMLAssociation extends DiagramEntity {
	private static final long serialVersionUID = 1L;
	@Id
	public long id;
	public boolean locked;
	public boolean limited;
	
	public String leftName;
	public String rightName;
	public String leftMultiplicity;
	public String rightMiltipliciry;
	public UMLAssociationType type;
	public Key<?> nodes[];
	
	public UMLAssociation(String name, Key<LDUser> createdBy) {
		super(name, createdBy);
		nodes = new Key<?>[2];
		// TODO Auto-generated constructor stub
	}
	
}
