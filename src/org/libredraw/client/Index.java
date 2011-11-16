package org.libredraw.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Index implements EntryPoint {
	
	private final LibreRPCAsync LibreRPCService = GWT
			.create(LibreRPC.class);
	
	private static Logger logger = Logger.getLogger("NameOfYourLogger");

	@Override
	public void onModuleLoad() {
		String sessionId = Cookies.getCookie("sid");
		if(sessionId == null)
			sessionId="NO_SESSION";
		LibreRPCService.login(sessionId,
				new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
				}
				public void onSuccess(String result) {
					logger.log(Level.SEVERE, "request is back");
					if(result == "true") {
						logger.log(Level.SEVERE, "true");
						TableView.navigateTo();
					} else {
						logger.log(Level.SEVERE, "false");
						Login.navigateTo();
					}
				}
		});
	}
}
