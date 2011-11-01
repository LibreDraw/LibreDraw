package org.libredraw.server.persistence;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

@Entity
public class P_AutoIncrement {
	
	private static Objectify ofy = ObjectifyService.begin();
	
	
	@Id public long id;
	public boolean locked;
	public boolean limited;
	
	public P_AutoIncrement() throws Exception {
		id = getNextId(this.getClass());
		locked = false;
		limited = false;
	}

	private long getNextId(Class<?> value) throws Exception {
		P_AutoIncrementRecord record;
		long id = P_AutoIncrementRecord.classToLong(value);
		if(id<0)
			throw new Exception("Bad class type"); //This should never happen!
		try {
			record = ofy.get(P_AutoIncrementRecord.class, id);
		} catch (NotFoundException e) {
			//make one if not in existence
			record = new P_AutoIncrementRecord(id);
		}
		long nextId = record.getNextId();
		ofy.put(record); //make sure to store changed record
		return nextId;
	}
	
}
