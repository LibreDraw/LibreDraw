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

import javax.jdo.annotations.Serialized;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UMLOperation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	public long id;

	String m_name;
	String m_returnType;
	String m_returnTypeMultiplicity;
	@Serialized
	Vector<UMLOperationParameter> m_parameters;
	UMLVisibility m_visibility;
	
	public UMLOperation() {
		m_parameters = new Vector<UMLOperationParameter>();
	}
	
	public String toString() {
		 String result = m_visibility.toString();
		 result += m_name;
		 result +="(";
		 if(m_parameters != null) {
			 int count = 0;
			 for(UMLOperationParameter p : m_parameters) {
				 if(count == 0)
					 result += p.toString();
				 else
					 result +=", " + p.toString();
				count++;
			 }
		 }
		 result += ")";
		 if(m_returnType != null) {
			 result += ": " + m_returnType;
			 if(m_returnTypeMultiplicity != null)
				 result += "[" + m_returnTypeMultiplicity + "]";
		 }
		return result;
	}
}
