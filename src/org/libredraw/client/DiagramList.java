package org.libredraw.client;

import org.libredraw.shared.Project;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.MenuItem;

public class DiagramList extends Composite {

	private static DiagramListUiBinder uiBinder = GWT
			.create(DiagramListUiBinder.class);
	@UiField(provided=true) CellTable<Object> table = new CellTable<Object>();
	@UiField MenuItem refreshMenu;
	@UiField MenuItem newDiagramMenu;
	@UiField MenuItem deleteMenu;
	@UiField MenuItem editMenu;
	@UiField static ScrollPanel scrollPanel;
	Project regarding=null;

	interface DiagramListUiBinder extends UiBinder<Widget, DiagramList> {
	}

	public DiagramList(Project p) {
		initWidget(uiBinder.createAndBindUi(this));
		
		regarding = p;
		
		DiagramList.onResize();
		
		Window.addResizeHandler(new ResizeHandler() {
			public void onResize(ResizeEvent event) {
				DiagramList.onResize();
			}
		});
		
		newDiagramMenu.setCommand(new Command() {
			@Override
			public void execute() {
				TableView.registerDialog(new NewDiagramDialog(regarding));
			}
		});
		
	}
	
	private static void onResize() {
		//Set height of scrollPanel widget window height - header - footer
		Integer windowHeight = Window.getClientHeight()-150;
		Integer windowWidth = Window.getClientWidth()-2;
		scrollPanel.setHeight(windowHeight.toString()+"px");
		scrollPanel.setWidth(windowWidth.toString()+"px");
	}
	
	private static void refresh() {
		
	}
}
