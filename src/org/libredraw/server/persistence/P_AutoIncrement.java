package org.libredraw.server.persistence;

import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public class P_AutoIncrement {
	
	private static Objectify ofy = ObjectifyService.begin();
	
	public long defaultId() {
		return getNextId(this.getClass());
	}

	private long getNextId(Class<?> value) {
		P_AutoIncrementRecord record;
		long id = P_AutoIncrementRecord.classToLong(value);
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
