package org.libredraw.client;

public final class Hash {
	public static native String sha1(String value) /*-{
		s = hex_sha1(value);
		$wnd.alert(s); 
		return s;
	}-*/;

}
