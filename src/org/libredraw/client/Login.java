package org.libredraw.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Login implements EntryPoint {
	
	public void onModuleLoad() {
		
		RootPanel.get("loginBox").add(new LoginBox());
		RootPanel.get("rainbow").add(new Rainbow());
	}
	
	public static void navigateTo() {
		Window.Location.replace("Login.html");
	}
}
