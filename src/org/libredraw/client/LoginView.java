/**
 * 
 */
package org.libredraw.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * @author Ethan
 *
 */
public class LoginView extends Composite {

	private static LoginViewUiBinder uiBinder = GWT
			.create(LoginViewUiBinder.class);
	@UiField Button loginButton;
	@UiField Button registerButton;
	RegisterView thisRegistration;

	interface LoginViewUiBinder extends UiBinder<Widget, LoginView> {
	}

	public LoginView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	@UiHandler("registerButton")
	void onRegisterButtonClick(ClickEvent event) {
		if(thisRegistration==null) {
			thisRegistration= new RegisterView();
			RootPanel.get("register").add(thisRegistration);
			thisRegistration.registerDialog.center();
		} else {
			thisRegistration.registerDialog.show();
		}
	}
}
