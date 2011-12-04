package org.libredraw.client.umlclassdiagram;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ListBox;

public class newAssociationDialog extends DialogBox {

	private static newAssociationDialogUiBinder uiBinder = GWT
			.create(newAssociationDialogUiBinder.class);
	@UiField ListBox entityOneComboBox;
	@UiField ListBox entityTwoComboBox;
	long thisBranch;

	interface newAssociationDialogUiBinder extends
			UiBinder<Widget, newAssociationDialog> {
	}

	public newAssociationDialog(long branch) {
		setWidget(uiBinder.createAndBindUi(this));
		
		thisBranch = branch;
	}

}
