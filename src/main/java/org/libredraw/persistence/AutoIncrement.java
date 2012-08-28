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

package org.libredraw.persistence;

import org.libredraw.persistence.umlclassdiagram.Association;
import org.libredraw.persistence.umlclassdiagram.Attribute;
import org.libredraw.persistence.umlclassdiagram.Classs;
import org.libredraw.persistence.umlclassdiagram.Enumeration;
import org.libredraw.persistence.umlclassdiagram.EnumerationLiteral;
import org.libredraw.persistence.umlclassdiagram.Interface;
import org.libredraw.persistence.umlclassdiagram.Note;
import org.libredraw.persistence.umlclassdiagram.Operation;
import org.libredraw.persistence.umlclassdiagram.OperationParameter;
import org.libredraw.persistence.umlclassdiagram.Package;

import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public final class AutoIncrement {
	
	private static Objectify m_ofy = ObjectifyService.begin();
	public static Class<?> m_table[] = {
		AutoIncrementRecord.class, //can't use zero
		Authorization.class, 
		Branch.class, 
		Diagram.class, 
		GenericAccountConnector.class, 
		LDUser.class, 
		Project.class, 
		Version.class, 
		Association.class, 
		Attribute.class, 
		Classs.class, 
		Enumeration.class, 
		EnumerationLiteral.class, 
		Interface.class, 
		Note.class, 
		Operation.class, 
		OperationParameter.class, 
		Package.class,
		Session.class,
		Permission.class
	};

	public static long getNextId(Class<?> value) {
		AutoIncrementRecord record;
		long id = classToLong(value);
		try {
			record = m_ofy.get(AutoIncrementRecord.class, id);
		} catch (NotFoundException e) {
			//make one if not in existence
			record = new AutoIncrementRecord(id);
		}
		long nextId = record.getNextId();
		m_ofy.put(record); //make sure to store changed record
		return nextId;
	}
	
	public static long classToLong(Class<?> value) {
		for(int i=0; i < m_table.length;i++)
			if(m_table[i] == value)
				return i;
		return -1;
	}
	
}
