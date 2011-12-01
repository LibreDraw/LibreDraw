package org.libredraw.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class BreadCrumb extends Composite {

	private static BreadCrumbUiBinder uiBinder = GWT
			.create(BreadCrumbUiBinder.class);

	interface BreadCrumbUiBinder extends UiBinder<Widget, BreadCrumb> {
	}

	public BreadCrumb() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
