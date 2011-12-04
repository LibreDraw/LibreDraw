package org.libredraw.client.umlclassdiagram;

import java.util.List;
import org.libredraw.shared.DiagramEntity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
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

	public newAssociationDialog(long branch, List<DiagramEntity> entities) {
		setWidget(uiBinder.createAndBindUi(this));
		
		thisBranch = branch;
		
		entityOneComboBox.setItemText(0, "Select and entity:");
		entityTwoComboBox.setItemText(0, "Select and entity:");
		
		int count = 1;
		for(DiagramEntity d : entities) {
			if(d.entityKey.getKind() != "UMLAssociation") {
				entityOneComboBox.setItemText(count, d.m_name);
				entityTwoComboBox.setItemText(count, d.m_name);
				count++;
			}
		}
	}
	
}
