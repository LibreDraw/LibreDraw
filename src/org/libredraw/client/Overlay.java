package org.libredraw.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class Overlay extends Composite {

	private static overlayUiBinder uiBinder = GWT.create(overlayUiBinder.class);
	@UiField Button CloseOverlay;
	@UiField DialogBox OverlayDialogBox;

	interface overlayUiBinder extends UiBinder<Widget, Overlay> {
	}

	public Overlay() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("CloseOverlay")
	void onCloseOverlayClick(ClickEvent event) {
		OverlayDialogBox.hide();
	}
}
