package org.libredraw.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.RootPanel;

public class TableView implements EntryPoint {
	
	private final LibreRPCAsync LibreRPCService = GWT
			.create(LibreRPC.class);
	
	static DialogBox dialog;
	static DialogBox errorDialog;

	@Override
	public void onModuleLoad() {
		
		String sessionId = ClientSession.getInstance().getSessionId();
		if(sessionId == null)
			sessionId="NO_SESSION";
		LibreRPCService.login(sessionId,
				new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
					errorDialog = new StackTrace(caught);
				}
				public void onSuccess(String result) {
					if(result == null) {
						Login.navigateTo();
					}
				}
		});
		
		RootPanel.get("body").add(new ProjectList());
		RootPanel.get("logout").add(new Logout());
	}
	
	public static void navigateTo() {
		Window.Location.assign("TableView.html");
	}
	
	public static void registerDialog(DialogBox d) {
		dialog = d;
	}
	
	public static void registerErrorDialog(DialogBox d) {
		errorDialog = d;
	}

}
