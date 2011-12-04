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

public class NewProjectDialog extends DialogBox {
	
	private final LibreRPCAsync LibreRPCService = GWT
			.create(LibreRPC.class);

	private static NewProjectDialogUiBinder uiBinder = GWT
			.create(NewProjectDialogUiBinder.class);
	@UiField TextBox nameTextBox;
	@UiField Button submitButton;
	@UiField Button cancelButton;
	@UiField Label errorLabel;

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
		if(nameTextBox.getText() == "")
			errorLabel.setText("Project name cannot be blank");
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
							if(result != null) {
								myHide();
								ProjectList.getInstace().refresh();
							} else
								errorLabel.setText("Create failed :'(");
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
}
