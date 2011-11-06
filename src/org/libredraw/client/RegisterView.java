/**
 * 
 */
package org.libredraw.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.AbsolutePanel;

/**
 * @author Ethan
 *
 */
public class RegisterView extends Composite {

	private static RegisterViewUiBinder uiBinder = GWT
			.create(RegisterViewUiBinder.class);
	@UiField Button submitButton;
	@UiField Button cancelButton;
	@UiField TextBox registerEmail;
	@UiField TextBox registerDisplayName;
	@UiField PasswordTextBox registerPassword;
	@UiField PasswordTextBox registerVerifyPassword;
	@UiField DialogBox registerDialog;
	@UiField Label errorLabel;
	@UiField AbsolutePanel overlay;
	
	private final LibreRPCAsync LibreRPCService = GWT
			.create(LibreRPC.class);

	interface RegisterViewUiBinder extends UiBinder<Widget, RegisterView> {
	}

	public RegisterView() {
		initWidget(uiBinder.createAndBindUi(this));
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
			LibreRPCService.register(registerEmail.getText(), Hash.sha1(registerPassword.getText()), registerDisplayName.getText(),
					new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						overlay.add(new StackTrace(caught));
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
		hide();
	}
	
	private void hide() {
		registerDialog.hide();
		registerEmail.setText("");
		registerDisplayName.setText("");
		registerPassword.setText("");
		registerVerifyPassword.setText("");
		errorLabel.setText("");
	}
	
	private native boolean validateEmail(String email) /*-{
		var emailPattern = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i;;
		return emailPattern.test(email);
	}-*/;
}
