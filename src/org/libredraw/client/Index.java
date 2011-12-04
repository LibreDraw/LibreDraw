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

//import java.util.logging.Level;
//import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;

public class Index implements EntryPoint {
	DialogBox errorDialog;
	
	private final LibreRPCAsync LibreRPCService = GWT
			.create(LibreRPC.class);
	
	//private static Logger logger = Logger.getLogger("NameOfYourLogger");

	@Override
	public void onModuleLoad() {
		String sessionId = Cookies.getCookie("SID");
		if(sessionId == null)
			sessionId="NO_SESSION";
		LibreRPCService.login(sessionId,
				new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
					errorDialog = new StackTrace(caught);
				}
				public void onSuccess(String result) {
					//logger.log(Level.SEVERE, "request is back");
					if(result == "true") {
						//logger.log(Level.SEVERE, "true");
						TableView.navigateTo();
					} else {
						//logger.log(Level.SEVERE, "false");
						Login.navigateTo();
					}
				}
		});
	}
}
