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
	
	private final LibreRPCAsync LibreRPCService = GWT
			.create(LibreRPC.class);

	interface RegisterViewUiBinder extends UiBinder<Widget, RegisterView> {
	}

	public RegisterView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("submitButton")
	void onSubmitButtonClick(ClickEvent event) {
		LibreRPCService.register(registerEmail.getText(), Hash.sha1(registerPassword.getText()), registerDisplayName.getText(),
				new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
					errorLabel.setText(caught.toString());
				}
				public void onSuccess(String result) {
					hide();
				}
		});
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
	}
}
