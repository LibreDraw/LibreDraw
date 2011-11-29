package org.libredraw.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;

public class NewDiagramDialog extends DialogBox {
	
	private final LibreRPCAsync LibreRPCService = GWT
			.create(LibreRPC.class);

	private static NewProjectDialogUiBinder uiBinder = GWT
			.create(NewProjectDialogUiBinder.class);
	@UiField TextBox nameTextBox;
	@UiField Button submitButton;
	@UiField Button cancelButton;
	@UiField Label errorLabel;
	@UiField ListBox typeCombo;

	interface NewProjectDialogUiBinder extends
			UiBinder<Widget, NewDiagramDialog> {
	}

	public NewDiagramDialog() {
		setWidget(uiBinder.createAndBindUi(this));
		this.setText("New diagram");
		this.setAnimationEnabled(true);
		this.setAnimationEnabled(true);
		this.setGlassEnabled(true);
		this.center();
		
		typeCombo.addItem("UML Class");
	}

	@UiHandler("submitButton")
	void onSubmitButtonClick(ClickEvent event) {
		/*if(nameTextBox.getText() == "")
			errorLabel.setText("Diagram name cannot be blank");
		else {
			errorLabel.setText("");
			LibreRPCService.createProject(
					ClientSession.getInstance().getSessionId(), 
					nameTextBox.getText(), 
					new AsyncCallback<String>() {
						public void onFailure(Throwable caught) {
							TableView.registerErrorDialog(new StackTrace(caught));
						}
						public void onSuccess(String result) {
							if(result != null)
								myHide();
							else
								errorLabel.setText("Create failed :'(");
						}
					});
		}*/
		//TODO Fix this
	}
	
	@UiHandler("cancelButton")
	void onCancelButtonClick(ClickEvent event) {
		myHide();
	}
	
	private void myHide() {
		this.hide();
		this.removeFromParent();
	}
}