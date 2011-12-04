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

import java.util.Date;

import org.libredraw.shared.Util;

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
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.CheckBox;

/**
 * @author Ethan
 *
 */
public class LoginBox extends Composite {

	private static LoginViewUiBinder uiBinder = GWT
			.create(LoginViewUiBinder.class);
	@UiField Button loginButton;
	@UiField Button registerButton;
	@UiField TextBox userEmail;
	@UiField PasswordTextBox userPassword;
	@UiField Label errorLabel;
	@UiField CheckBox rememberCheckbox;
	
	private final LibreRPCAsync LibreRPCService = GWT
			.create(LibreRPC.class);

	interface LoginViewUiBinder extends UiBinder<Widget, LoginBox> {
	}

	public LoginBox() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	@UiHandler("registerButton")
	void onRegisterButtonClick(ClickEvent event) {	
		Login.registerDialog(new RegisterDialog());
	}
	@UiHandler("loginButton")
	void onLoginButtonClick(ClickEvent event) {
		if(userEmail.getText()=="") {
			errorLabel.setText("Please enter a valid e-mail.");
		}
		else if(userPassword.getText()=="") {
			errorLabel.setText("Please enter a password.");
		}
		else {
			errorLabel.setText("");
			loginCheck();
		}
	}
	@UiHandler("userEmail")
	void onUserEmailKeyPress(KeyPressEvent event) {
		if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_TAB) {
			userPassword.setFocus(true);
		}
	}
	@UiHandler("userPassword")
	void onUserPasswordKeyPress(KeyPressEvent event) {
		if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
			loginCheck();
		}
	}
	
	private void loginCheck() {
		LibreRPCService.login(userEmail.getText(), Util.sha1(userPassword.getText()),
				new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
					Login.registerErrorDialog(new StackTrace(caught));
				}
				public void onSuccess(String result) {
					if(result != null) {
						errorLabel.setText("Login Sucsess");
						long DURATION = 1000 * 60 * 60 * 24 * 14;
						Date expires = new Date(System.currentTimeMillis() + DURATION);
						if(rememberCheckbox.getValue())
							Cookies.setCookie("SID", result, expires, null, "/", false);
						TableView.navigateTo();
					}else{
						errorLabel.setText("Bad Login");
					}
				}
		});
	}
}
