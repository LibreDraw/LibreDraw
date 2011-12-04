/*
	This file is part of LibreDraw.

    LibreDraw is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    LibreDraw is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with LibreDraw.  If not, see <http://www.gnu.org/licenses/>.
*/

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
						if(result != "true")
							m_sessionId = null;
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
