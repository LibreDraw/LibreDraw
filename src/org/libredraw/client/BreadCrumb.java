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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class BreadCrumb extends Composite {

	private static BreadCrumbUiBinder uiBinder = GWT
			.create(BreadCrumbUiBinder.class);
	private static BreadCrumb m_instance = null;

	interface BreadCrumbUiBinder extends UiBinder<Widget, BreadCrumb> {
	}

	private BreadCrumb() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public BreadCrumb getInstance() {
		if(m_instance == null)
			m_instance = new BreadCrumb();
		return m_instance;
	}

}
