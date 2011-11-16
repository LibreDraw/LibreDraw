package org.libredraw.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class NewProjectDialog extends Composite {

	private static NewProjectDialogUiBinder uiBinder = GWT
			.create(NewProjectDialogUiBinder.class);

	interface NewProjectDialogUiBinder extends
			UiBinder<Widget, NewProjectDialog> {
	}

	public NewProjectDialog() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
