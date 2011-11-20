package org.libredraw.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class Logout extends Composite {

	private static LogoutUiBinder uiBinder = GWT.create(LogoutUiBinder.class);
	@UiField Button logoutButton;

	interface LogoutUiBinder extends UiBinder<Widget, Logout> {
	}

	public Logout() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("logoutButton")
	void onLogoutButtonClick(ClickEvent event) {
		Cookies.removeCookie("SID");
		Login.navigateTo();
	}
}
