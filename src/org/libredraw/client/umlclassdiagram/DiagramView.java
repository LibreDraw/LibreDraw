package org.libredraw.client.umlclassdiagram;

import org.libredraw.client.TableView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.DecoratedTabPanel;

public class DiagramView extends Composite {

	private static DiagramViewUiBinder uiBinder = GWT
			.create(DiagramViewUiBinder.class);
	@UiField(provided=true) CellTable<Object> table = new CellTable<Object>();
	@UiField MenuItem newEnumerationMenu;
	@UiField MenuItem newInterfaceMenu;
	@UiField MenuItem newClassMenu;
	@UiField MenuItem refreshMenu;
	@UiField MenuItem menuNewAssociation;
	@UiField MenuItem newPackageMenu;
	@UiField MenuItem saveMenu;
	@UiField MenuItem modifyMenu;
	@UiField MenuItem deletMenu;
	@UiField MenuItem permissionMenu;
	@UiField MenuItem codeMenu;
	@UiField MenuItem versionsMenu;
	@UiField HTML canvas;
	@UiField ScrollPanel scrollPanel;
	@UiField static DecoratedTabPanel tabPanel;
	long thisBranch;

	interface DiagramViewUiBinder extends UiBinder<Widget, DiagramView> {
	}

	public DiagramView(long branch) {
		initWidget(uiBinder.createAndBindUi(this));
		
		thisBranch = branch;
		
		tabPanel.selectTab(0);
		
		onResize();
		
		Window.addResizeHandler(new ResizeHandler() {
			public void onResize(ResizeEvent event) {
				DiagramView.onResize();
			}
		});

		newClassMenu.setCommand(new Command() {
			@Override
			public void execute() {
				TableView.registerDialog(new newClassDialog());
			}
		});
	}

	private static void onResize() {
		//Set height of scrollPanel widget window height - header - footer
		Integer windowHeight = Window.getClientHeight()-150;
		Integer windowWidth = Window.getClientWidth()-2;
		tabPanel.setHeight(windowHeight.toString()+"px");
		tabPanel.setWidth(windowWidth.toString()+"px");
	}
}
