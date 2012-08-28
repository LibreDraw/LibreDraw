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
import java.util.Vector;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.libredraw.persistence.LDUser;

import com.googlecode.objectify.Key;

@Entity
public class Enumeration extends Node implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id public long id;
	
	transient public Vector<Key<EnumerationLiteral>> m_literals;
	
	@Transient public Vector<EnumerationLiteral> literals;
	
	public Enumeration(String name, Visibility visibility, Vector<EnumerationLiteral> literals, Key<LDUser> createdBy) {
		super(name, visibility, createdBy);
		this.literals = literals;
	}

}
