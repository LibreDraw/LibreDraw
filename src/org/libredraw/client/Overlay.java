/*
	This file is part of LibreDraw.

    LibreDraw is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    LibreDraw is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with LibreDraw.  If not, see <http://www.gnu.org/licenses/>.
*/

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
