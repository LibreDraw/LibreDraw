package org.libredraw.server.persistence;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.libredraw.server.persistence.umlclassdiagram.*;

@Entity
public class P_AutoIncrementRecord {

	@Id public long m_entiyType;
	private long m_nextId = 0;
	private static Class<?> m_table[] = {
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
		P_UMLPackage.class, 
		P_UMLVisibility.class
	};
	
	public P_AutoIncrementRecord(long id) {
		m_entiyType = id;
	}
	
	public long getNextId() {
		return m_nextId++;
	}
	
	public static long classToLong(Class<?> value) {
		for(int i=0; i < m_table.length;i++)
			if(m_table[i] == value)
				return i;
		return -1;
	}

}
