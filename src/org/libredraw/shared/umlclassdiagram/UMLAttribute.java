package org.libredraw.shared.umlclassdiagram;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UMLAttribute implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
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

}
