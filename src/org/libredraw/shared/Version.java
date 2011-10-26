package org.libredraw.shared;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

public class Version extends AutoIncrement implements Serializable {
	public String tag;
	public int versionNuber;
	public Date date;
	public Vector<Key> diagram;
	public Key previousVersion;
	public Key modified;
}
