package org.libredraw.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LibreDraw implements EntryPoint {

	public void onModuleLoad() {
		LoginView myScreen = new LoginView();
		RootPanel.get("loginBox").add(myScreen);
	}
}
