package org.libredraw.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;

public class StackTrace extends DialogBox {

	private static StackTraceUiBinder uiBinder = GWT
			.create(StackTraceUiBinder.class);
	@UiField Button closeButton;
	@UiField HTML htmlPanel;

	interface StackTraceUiBinder extends UiBinder<Widget, StackTrace> {
	}

	public StackTrace(Throwable caught) {
		setWidget(uiBinder.createAndBindUi(this));
		this.setText("Stacktrace");
		this.setAnimationEnabled(true);
		this.setAnimationEnabled(true);
		this.setGlassEnabled(true);
		this.center();
		
		String output = caught.getClass().getName() + caught.getLocalizedMessage();
		for (StackTraceElement ste : caught.getStackTrace())
			output += "</br>" + ste.toString();
		
		htmlPanel.setHTML(output);
	}

	@UiHandler("closeButton")
	void onCloseButtonClick(ClickEvent event) {
		this.hide();
		this.removeFromParent();
	}
}
