package org.libredraw.client;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;

public class DialogOverlay extends AbsolutePanel {
	private static DialogOverlay m_instance;
	
	private DialogOverlay() {
		super();
		this.setHeight("100%");
		this.setWidth("100%");
		RootPanel.get("overlay").add(this);
	}
	
	public static DialogOverlay getInstance() {
		if(m_instance == null)
			m_instance = new DialogOverlay();
		return m_instance;
	}

}
