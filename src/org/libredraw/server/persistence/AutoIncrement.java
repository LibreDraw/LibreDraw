package org.libredraw.server.persistence;

import org.libredraw.shared.Branch;
import org.libredraw.shared.Diagram;
import org.libredraw.shared.GenericAccountConnector;
import org.libredraw.shared.LDUser;
import org.libredraw.shared.Project;
import org.libredraw.shared.Version;
import org.libredraw.shared.umlclassdiagram.UMLAssociation;
import org.libredraw.shared.umlclassdiagram.UMLAttribute;
import org.libredraw.shared.umlclassdiagram.UMLClass;
import org.libredraw.shared.umlclassdiagram.UMLEnumeration;
import org.libredraw.shared.umlclassdiagram.UMLEnumerationLiteral;
import org.libredraw.shared.umlclassdiagram.UMLInterface;
import org.libredraw.shared.umlclassdiagram.UMLNote;
import org.libredraw.shared.umlclassdiagram.UMLOperation;
import org.libredraw.shared.umlclassdiagram.UMLOperationParameter;
import org.libredraw.shared.umlclassdiagram.UMLPackage;

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
		UMLAssociation.class, 
		UMLAttribute.class, 
		UMLClass.class, 
		UMLEnumeration.class, 
		UMLEnumerationLiteral.class, 
		UMLInterface.class, 
		UMLNote.class, 
		UMLOperation.class, 
		UMLOperationParameter.class, 
		UMLPackage.class,
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
