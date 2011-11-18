package org.libredraw.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.RootPanel;

public class TableView implements EntryPoint {
	static DialogBox dialog;
	static DialogBox errorDialog;

	@Override
	public void onModuleLoad() {
		RootPanel.get("body").add(new ProjectList());
	}
	
	public static void navigateTo() {
		Window.Location.assign("TableView.html");
	}
	
	public static void registerDialog(DialogBox d) {
		dialog = d;
	}
	
	public static void registerErrorDialog(DialogBox d) {
		errorDialog = d;
	}

}
