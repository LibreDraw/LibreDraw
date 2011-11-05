package org.libredraw.server.persistence;

import org.libredraw.server.persistence.umlclassdiagram.*;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public final class P_AutoIncrement {
	
	private static Objectify m_ofy = ObjectifyService.begin();
	public static Class<?> m_table[] = {
		P_AutoIncrementRecord.class,
		P_Authorization.class, 
		P_Branch.class, 
		P_Diagram.class, 
		P_GenericAccountConnector.class, 
		P_LDUser.class, 
		P_Project.class, 
		P_Version.class, 
		P_UMLAssociation.class, 
		P_UMLAttribute.class, 
		P_UMLClass.class, 
		P_UMLEnumeration.class, 
		P_UMLEnumerationLiteral.class, 
		P_UMLInterface.class, 
		P_UMLNote.class, 
		P_UMLOperation.class, 
		P_UMLOperationParameter.class, 
		P_UMLPackage.class
	};

	public static long getNextId(Class<?> value) {
		P_AutoIncrementRecord record;
		long id = classToLong(value);
		try {
			record = m_ofy.get(P_AutoIncrementRecord.class, id);
		} catch (NotFoundException e) {
			//make one if not in existence
			record = new P_AutoIncrementRecord(id);
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
