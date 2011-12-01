package org.libredraw.client;

import java.util.Date;
import java.util.List;

import org.libredraw.client.umlclassdiagram.DiagramView;
import org.libredraw.shared.Diagram;
import org.libredraw.shared.Project;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.ListDataProvider;

public class DiagramList extends Composite {
	
	private final LibreRPCAsync LibreRPCService = GWT
			.create(LibreRPC.class);

	private static DiagramListUiBinder uiBinder = GWT
			.create(DiagramListUiBinder.class);
	@UiField(provided=true) CellTable<Diagram> table = new CellTable<Diagram>();
	@UiField MenuItem refreshMenu;
	@UiField MenuItem newDiagramMenu;
	@UiField MenuItem deleteMenu;
	@UiField MenuItem editMenu;
	@UiField static ScrollPanel scrollPanel;
	Project thisProject = null;
	List<Diagram> diagramList;
	Date clickTracker = null;

	interface DiagramListUiBinder extends UiBinder<Widget, DiagramList> {
	}

	public DiagramList(Project project) {
		initWidget(uiBinder.createAndBindUi(this));
		
		thisProject = project;
		
		DiagramList.onResize();
		
		Window.addResizeHandler(new ResizeHandler() {
			public void onResize(ResizeEvent event) {
				DiagramList.onResize();
			}
		});
		
		Column<Diagram, Boolean> checkColumn = new Column<Diagram, Boolean>( 
			new CheckboxCell(true, false)) {
				@Override
				public Boolean getValue(Diagram d) {
					return false;
				}
		};
		
		TextColumn<Diagram> nameColumn = new TextColumn<Diagram>() {
			@Override
			public String getValue(Diagram d) {
				return d.m_name;
			}
		};
		
		TextColumn<Diagram> typeColumn = new TextColumn<Diagram>() {
			@Override
			public String getValue(Diagram d) {
				return d.m_type.toString();
			}
		};
				
		TextColumn<Diagram> modifiedColumn = new TextColumn<Diagram>() {
			@Override
			public String getValue(Diagram d) {
				return d.m_modifiedDate.toString();
			}
		};
				
		TextColumn<Diagram> modifiedByColumn = new TextColumn<Diagram>() {
			@Override
			public String getValue(Diagram d) {
				return d.m_modifiedBy.m_displayName;
			}
		};
		
		TextColumn<Diagram> createdOnColumn = new TextColumn<Diagram>() {
			@Override
			public String getValue(Diagram d) {
				return d.m_createdDate.toString();
			}
		};
				
		TextColumn<Diagram> ownerColumn = new TextColumn<Diagram>() {
			@Override
			public String getValue(Diagram d) {
				return d.m_owner.m_displayName;
			}
		};
		
		table.addColumn(checkColumn, "");
		table.addColumn(nameColumn, "Name");
		table.addColumn(typeColumn, "Type");
		table.addColumn(modifiedColumn, "Date Modified");
		table.addColumn(modifiedByColumn, "By");
		table.addColumn(createdOnColumn, "Created On");
		table.addColumn(ownerColumn, "Owner");
		
		table.setWidth("100%", true);
		table.setColumnWidth(checkColumn, 50.0, Unit.PX);
		
		table.addCellPreviewHandler(new CellPreviewEvent.Handler<Diagram>() {
			@Override
			public void onCellPreview(CellPreviewEvent<Diagram> event) {
				//TODO get a real double click event
				if("click".equals(event.getNativeEvent().getType())) {
					if(event.getContext().getColumn() != 0) {
						if(clickTracker == null)
							clickTracker = new Date();
						else {
							long difference = new Date().getTime() - clickTracker.getTime();
							if(difference <= 500){
								Diagram thisProject = event.getValue();
								RootPanel.get("body").add(new DiagramView(thisProject.m_master));
								myRemove();
							}
							else
								clickTracker = new Date();
						}
					}
				}
			}
		});
		
		newDiagramMenu.setCommand(new Command() {
			@Override
			public void execute() {
				TableView.registerDialog(new NewDiagramDialog(thisProject));
			}
		});
		
		refreshMenu.setCommand(new Command() {
			@Override
			public void execute() {
				refreshTable();
			}
		});
		
		refreshTable();
		
	}
	
	private static void onResize() {
		//Set height of scrollPanel widget window height - header - footer
		Integer windowHeight = Window.getClientHeight()-150;
		Integer windowWidth = Window.getClientWidth()-2;
		scrollPanel.setHeight(windowHeight.toString()+"px");
		scrollPanel.setWidth(windowWidth.toString()+"px");
	}
	
	private void refreshTable() {
		LibreRPCService.getDiagramList(ClientSession.getInstance().getSessionId(), thisProject.m_id, 
				new AsyncCallback<List<Diagram>>() {
			@Override
			public void onFailure(Throwable caught) {
				TableView.registerErrorDialog(new StackTrace(caught));
			}
			@Override
			public void onSuccess(List<Diagram> result) {
				diagramList = result;
				populateTable(diagramList);
			}
		});
	}
	
	private void populateTable(List<Diagram> diagrams) {
		ListDataProvider<Diagram> dataProvider = new ListDataProvider<Diagram>();
		
		dataProvider.addDataDisplay(table);
		
		List<Diagram> list = dataProvider.getList();
		for (Diagram d : diagrams) {
	    	list.add(d);
		}
	}
	
	private void myRemove() {
		this.removeFromParent();
	}
}
