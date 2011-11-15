package org.libredraw.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Login implements EntryPoint {
	
	private final LibreRPCAsync LibreRPCService = GWT
			.create(LibreRPC.class);

	public void onModuleLoad() {
		final String sessionID = Cookies.getCookie("sid");
		if(sessionID != null)
		{
			LibreRPCService.login(sessionID,
					new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						RootPanel.get("errorOverlay").add(new StackTrace(caught));
					}
					public void onSuccess(String result) {
						if(result != null)
							TableView.navigateTo();
					}
			});
		}
		
		RootPanel.get("loginBox").add(new LoginBox());
		RootPanel.get("rainbow").add(new Rainbow());
	}
}
