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

import org.libredraw.client.umlclassdiagram.DiagramView;
import org.libredraw.shared.Project;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Label;

public class BreadCrumb extends Composite {

	private static BreadCrumbUiBinder uiBinder = GWT
			.create(BreadCrumbUiBinder.class);
	@UiField Hyperlink projectHyperLink;
	@UiField Hyperlink projectListHyperLink;
	@UiField Label projectCarrot;
	@UiField Label diagramCarrot;
	@UiField Label diagramText;
	@UiField Label projectListText;
	private static BreadCrumb m_instance = null;
	Project thisProject;

	interface BreadCrumbUiBinder extends UiBinder<Widget, BreadCrumb> {
	}

	private BreadCrumb() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public static BreadCrumb getInstance() {
		if(m_instance == null)
			m_instance = new BreadCrumb();
		return m_instance;
		
		
	}
	@UiHandler("projectListHyperLink")
	void onProjectListHyperLinkClick(ClickEvent event) {
		DiagramView.getInstance().removeFromParent();
		DiagramList.getInstance().removeFromParent();
		RootPanel.get("body").add(ProjectList.getInstance());
		projectHyperLink.setVisible(false);
		projectCarrot.setVisible(false);
		diagramText.setVisible(false);
		diagramCarrot.setVisible(false);
		projectListText.setVisible(true);
		projectListHyperLink.setVisible(false);
	}
	@UiHandler("projectHyperLink")
	void onProjectHyperLinkClick(ClickEvent event) {
		DiagramView.getInstance().removeFromParent();
		DiagramList.getInstance().setProject(thisProject.id);
		RootPanel.get("body").add(DiagramList.getInstance());
		diagramText.setVisible(false);
		diagramCarrot.setVisible(false);
	}
	
	public void registerProject(Project p) {
		thisProject = p;
		projectHyperLink.setText(thisProject.m_name);
		projectHyperLink.setVisible(true);
		projectCarrot.setVisible(true);
		projectListText.setVisible(false);
		projectListHyperLink.setVisible(true);
	}
	
	public void registerDiagram(String name, String branchName) {
		diagramText.setText(name + "(" + branchName +")");
		diagramText.setVisible(true);
		diagramCarrot.setVisible(true);
	}
}
