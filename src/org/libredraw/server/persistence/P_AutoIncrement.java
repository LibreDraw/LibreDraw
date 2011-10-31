package org.libredraw.server.persistence;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class P_AutoIncrement {
	
	public String EntityType;
	@Id public long id;
	public boolean locked;
	public boolean limited;
	
	public P_AutoIncrement() {
	}

}
