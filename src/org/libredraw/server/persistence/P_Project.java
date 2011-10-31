package org.libredraw.server.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

public class P_Project extends P_AutoIncrement {
	public String name;
	public Date createdDate;
	public Vector<P_Key> diagrams;
	public P_Key owner;
}
