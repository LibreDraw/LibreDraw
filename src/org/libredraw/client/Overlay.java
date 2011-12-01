package org.libredraw.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class Overlay extends DialogBox {

	private static overlayUiBinder uiBinder = GWT.create(overlayUiBinder.class);
	@UiField Button CloseOverlay;

	interface overlayUiBinder extends UiBinder<Widget, Overlay> {
	}

	public Overlay() {
		setWidget(uiBinder.createAndBindUi(this));
		this.setText("Overlay");
		this.setAnimationEnabled(true);
		this.setAnimationEnabled(true);
		this.setGlassEnabled(true);
		this.center();
	}

	@UiHandler("CloseOverlay")
	void onCloseOverlayClick(ClickEvent event) {
		this.hide();
		this.removeFromParent();
	}
}
