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
		
		RootPanel.get("body").add(ProjectList.getInstance());
		RootPanel.get("logout").add(new Logout());
		RootPanel.get("breadCrumbs").add(BreadCrumb.getInstance());
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
