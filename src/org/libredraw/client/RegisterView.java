/**
 * 
 */
package org.libredraw.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.DialogBox;

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

	interface RegisterViewUiBinder extends UiBinder<Widget, RegisterView> {
	}

	public RegisterView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("submitButton")
	void onSubmitButtonClick(ClickEvent event) {
	}
	@UiHandler("cancelButton")
	void onCancelButtonClick(ClickEvent event) {
		registerDialog.hide();
		registerEmail.setText("");
		registerDisplayName.setText("");
		registerPassword.setText("");
		registerVerifyPassword.setText("");
	}
}
