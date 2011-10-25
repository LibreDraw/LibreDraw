package org.libredraw.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class Rainbow extends Composite {

	private static rainbowUiBinder uiBinder = GWT.create(rainbowUiBinder.class);
	@UiField Image image;
	Overlay overlay;

	interface rainbowUiBinder extends UiBinder<Widget, Rainbow> {
	}

	public Rainbow() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("image")
	void onImageClick(ClickEvent event) {
		if(overlay == null) {
			overlay = new Overlay();
			RootPanel.get("overlay").add(overlay);
			overlay.OverlayDialogBox.center();
			overlay.OverlayDialogBox.show();
		} else {
			overlay.OverlayDialogBox.show();
		}
	}
}