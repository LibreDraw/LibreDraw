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

/**
 * @author Ethan
 *
 */
public class RegisterView extends Composite {

	private static RegisterViewUiBinder uiBinder = GWT
			.create(RegisterViewUiBinder.class);
	@UiField Button submitButton;
	@UiField Button button;
	@UiField TextBox userRegisterEmail;
	@UiField TextBox RegisterEmail;
	@UiField PasswordTextBox RegisterPassword;
	@UiField PasswordTextBox VerifyPassword;

	interface RegisterViewUiBinder extends UiBinder<Widget, RegisterView> {
	}

	public RegisterView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("submitButton")
	void onSubmitButtonClick(ClickEvent event) {
	}
	@UiHandler("button")
	void onButtonClick(ClickEvent event) {
	}
}
