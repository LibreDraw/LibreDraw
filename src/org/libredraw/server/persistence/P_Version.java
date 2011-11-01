package org.libredraw.server.persistence;

import java.util.Date;
import java.util.Vector;

public class P_Version extends P_AutoIncrement {
	public P_Version() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	public String tag;
	public int versionNuber;
	public Date date;
	public Vector<P_Key> diagram;
	public P_Key previousVersion;
	public P_Key modified;
}
