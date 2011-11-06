/**
 * 
 */
package org.libredraw.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
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
	RegisterView thisRegistration;
	
	private final LibreRPCAsync LibreRPCService = GWT
			.create(LibreRPC.class);

	interface LoginViewUiBinder extends UiBinder<Widget, LoginBox> {
	}

	public LoginBox() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	@UiHandler("registerButton")
	void onRegisterButtonClick(ClickEvent event) {
		if(thisRegistration==null) {
			thisRegistration= new RegisterView();
			RootPanel.get("overlay").add(thisRegistration);
			thisRegistration.registerDialog.center();
		} else {
			thisRegistration.registerDialog.show();
		}
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
		LibreRPCService.login(userEmail.getText(), Hash.sha1(userPassword.getText()),
				new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
					RootPanel.get("overlay").add(new StackTrace(caught));
				}
				public void onSuccess(String result) {
					if(result == "Sucsess") {
						errorLabel.setText("Login Sucsess");
					}
				}
		});
	}
}
