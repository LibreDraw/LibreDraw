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

@Entity
public class UMLAttribute implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	public long id;

	String m_name;
	String m_type;
	String m_multiplicity;
	UMLVisibility m_visibility;
	
	String m_representation;
	
	public UMLAttribute() {
		// TODO Auto-generated constructor stub
	}
	
	public String toString() {
		 String result = m_visibility.toString();
		 result += m_name;
		 result += ": " + m_type;
		 if(m_multiplicity != null)
			 result += "[" + m_multiplicity + "]";
		return result;
	}

}
