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

import org.libredraw.shared.Util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;

/**
 * @author Ethan
 *
 */
public class RegisterDialog extends DialogBox {

	private static RegisterDialogUiBinder uiBinder = GWT
			.create(RegisterDialogUiBinder.class);
	@UiField Button submitButton;
	@UiField Button cancelButton;
	@UiField TextBox registerEmail;
	@UiField TextBox registerDisplayName;
	@UiField PasswordTextBox registerPassword;
	@UiField PasswordTextBox registerVerifyPassword;
	@UiField Label errorLabel;
	
	private final LibreRPCAsync LibreRPCService = GWT
			.create(LibreRPC.class);

	interface RegisterDialogUiBinder extends UiBinder<Widget, RegisterDialog> {
	}

	public RegisterDialog() {
		this.setWidget(uiBinder.createAndBindUi(this));
		this.setText("Register (all fields required)");
		this.setAnimationEnabled(true);
		this.setAnimationEnabled(true);
		this.setGlassEnabled(true);
		this.center();
	}

	@UiHandler("submitButton")
	void onSubmitButtonClick(ClickEvent event) {
		if(registerEmail.getText() == "" || 
				registerDisplayName.getText() == "" || 
				registerPassword.getText() == "" || 
				registerVerifyPassword.getText() == "") {
			errorLabel.setText("All fields are required");
		} else if(registerPassword.getText() != registerVerifyPassword.getText()) {
			errorLabel.setText("Passwords do not match.");
		} else if(!validateEmail(registerEmail.getText())) {
			errorLabel.setText("Invalid email");
		} else {
			errorLabel.setText("");
			LibreRPCService.register(registerEmail.getText(), Util.sha1(registerPassword.getText()), registerDisplayName.getText(),
					new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						Login.registerErrorDialog(new StackTrace(caught));
					}
					public void onSuccess(String result) {
						if(result =="Sucsess")
							hide();
						else if (result=="email")
							errorLabel.setText("Email in use.");
						else if (result=="name")
							errorLabel.setText("Display name in use.");
						else
							errorLabel.setText(result);
					}
			});
		}
	}
	
	@UiHandler("cancelButton")
	void onCancelButtonClick(ClickEvent event) {
		myHide();
	}
	
	private void myHide() {
		this.hide();
		this.removeFromParent();
	}
	
	private native boolean validateEmail(String email) /*-{
		var emailPattern = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i;;
		return emailPattern.test(email);
	}-*/;
}
