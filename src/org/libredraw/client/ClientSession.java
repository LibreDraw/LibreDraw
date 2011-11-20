package org.libredraw.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ClientSession {
	
	private final LibreRPCAsync LibreRPCService = GWT
			.create(LibreRPC.class);
	
	private static ClientSession m_instance;
	private static String m_sessionId;
	
	private ClientSession() {
		String sessionId = Cookies.getCookie("SID");
		if(sessionId != null) {
			m_sessionId = Cookies.getCookie("SID");
			LibreRPCService.login(sessionId,
					new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
					}
					public void onSuccess(String result) {
						m_sessionId = result;
					}
			});
		} else
			m_sessionId = null;
	}
	
	public static ClientSession getInstance() {
		if(m_instance == null)
			m_instance = new ClientSession();
		return m_instance;
	}
	
	public boolean isValid() {
		if(m_sessionId != null)
			return true;
		else
			return false;
	}
	
	public String getSessionId() {
		return m_sessionId;
	}

}
