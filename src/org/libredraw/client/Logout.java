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
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class Logout extends Composite {
	
	private final LibreRPCAsync LibreRPCService = GWT
			.create(LibreRPC.class);

	private static LogoutUiBinder uiBinder = GWT.create(LogoutUiBinder.class);
	@UiField Button logoutButton;

	interface LogoutUiBinder extends UiBinder<Widget, Logout> {
	}

	public Logout() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("logoutButton")
	void onLogoutButtonClick(ClickEvent event) {
		String sessionId = Cookies.getCookie("SID");
		LibreRPCService.endSession(sessionId, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				TableView.registerErrorDialog(new StackTrace(caught));
			}
			public void onSuccess(String result) {
				Cookies.removeCookie("SID");
				Login.navigateTo();
			}
		});
	}
}
