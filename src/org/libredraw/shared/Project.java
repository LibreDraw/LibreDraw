package org.libredraw.shared;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

public class Project extends AutoIncrement implements Serializable {
	public String name;
	public Date createdDate;
	public Vector<Key> diagrams;
	public Key owner;
}
