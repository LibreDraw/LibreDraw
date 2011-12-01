package org.libredraw.shared.umlclassdiagram;

import java.io.Serializable;

public class UMLAttribute implements Serializable
{
	public long id;
	public boolean locked;
	public boolean limited;

	String m_name;
	String m_type;
	String m_multiplicity;
	UMLVisibility m_visibility;
	
	String m_representation;
	
	public UMLAttribute() {
		// TODO Auto-generated constructor stub
	}
	
	//- Private + Public # Protected ~ Package
	public UMLAttribute(String representation) {
		
	}

}
