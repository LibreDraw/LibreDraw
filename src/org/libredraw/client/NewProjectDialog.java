package org.libredraw.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class NewProjectDialog extends DialogBox {

	private static NewProjectDialogUiBinder uiBinder = GWT
			.create(NewProjectDialogUiBinder.class);
	@UiField TextBox nameTextBox;
	@UiField Button submitButton;
	@UiField Button cancelButton;

	interface NewProjectDialogUiBinder extends
			UiBinder<Widget, NewProjectDialog> {
	}

	public NewProjectDialog() {
		setWidget(uiBinder.createAndBindUi(this));
		this.setText("New project");
		this.setAnimationEnabled(true);
		this.setAnimationEnabled(true);
		this.setGlassEnabled(true);
		this.center();
	}

	@UiHandler("submitButton")
	void onSubmitButtonClick(ClickEvent event) {
	}
	
	@UiHandler("cancelButton")
	void onCancelButtonClick(ClickEvent event) {
		this.hide();
		this.removeFromParent();
	}
}
