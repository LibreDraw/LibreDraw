package org.libredraw.client;

import java.util.Date;
import java.util.List;
import org.libredraw.shared.Project;
import com.gargoylesoftware.htmlunit.javascript.host.Event;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.ListDataProvider;

public class ProjectList extends Composite {
	
	private final LibreRPCAsync LibreRPCService = GWT
			.create(LibreRPC.class);

	private static ProjectListUiBinder uiBinder = GWT
			.create(ProjectListUiBinder.class);
	@UiField(provided=true) CellTable<Project> table = new CellTable<Project>();
	@UiField static ScrollPanel scrollPanel;
	@UiField MenuItem newProjectMenu;
	@UiField MenuItem archiveMenu;
	@UiField MenuItem editMenu;
	@UiField MenuItem deleteMenu;
	@UiField MenuItem exportMenu;
	@UiField MenuItem refreshMenu;
	List<Project> projectList;
	Date clickTracker = null;

	interface ProjectListUiBinder extends UiBinder<Widget, ProjectList> {
	}
	
	public ProjectList() {
		initWidget(uiBinder.createAndBindUi(this));
				
		refreshTable();

		ProjectList.onResize();
		
		Window.addResizeHandler(new ResizeHandler() {
			public void onResize(ResizeEvent event) {
				ProjectList.onResize();
			}
		});
		
		Column<Project, Boolean> checkColumn = new Column<Project, Boolean>( 
			new CheckboxCell(true, false)) {
				@Override
				public Boolean getValue(Project object) {
					return false;
				}
		};
				
		TextColumn<Project> nameColumn = new TextColumn<Project>() {
			@Override
			public String getValue(Project p) {
				return p.m_name;
			}
		};
				
		TextColumn<Project> modifiedColumn = new TextColumn<Project>() {
			@Override
			public String getValue(Project p) {
				return p.m_modified.toString();
			}
		};
				
		TextColumn<Project> modifiedByColumn = new TextColumn<Project>() {
			@Override
			public String getValue(Project p) {
				return p.m_modifedBy.m_displayName;
			}
		};
		
		TextColumn<Project> createdOnColumn = new TextColumn<Project>() {
			@Override
			public String getValue(Project p) {
				return p.m_createdDate.toString();
			}
		};
				
		TextColumn<Project> ownerColumn = new TextColumn<Project>() {
			@Override
			public String getValue(Project p) {
				return p.m_owner.m_displayName;
			}
		};
		table.addColumn(checkColumn, "");
		table.addColumn(nameColumn, "Name");
		table.addColumn(modifiedColumn, "Date Modified");
		table.addColumn(modifiedByColumn, "By");
		table.addColumn(createdOnColumn, "Created On");
		table.addColumn(ownerColumn, "Owner");
		
		table.setWidth("100%", true);
		table.setColumnWidth(checkColumn, 50.0, Unit.PX);
		
		table.sinkEvents(Event.DBLCLICK);
		
		table.addCellPreviewHandler(new CellPreviewEvent.Handler<Project>() {
			@Override
			public void onCellPreview(CellPreviewEvent<Project> event) {
				//TODO get a real double click event
				if("click".equals(event.getNativeEvent().getType())) {
					if(event.getContext().getColumn() != 0) {
						if(clickTracker == null)
							clickTracker = new Date();
						else {
							long difference = new Date().getTime() - clickTracker.getTime();
							if(difference <= 500){
								Project thisProject = event.getValue();
								RootPanel.get("body").add(new DiagramList(thisProject));
								myRemove();
							}
							else
								clickTracker = new Date();
						}
					}
				}
			}
		});
		
		newProjectMenu.setCommand(new Command() {
		   public void execute() {
			     TableView.registerDialog(new NewProjectDialog());
			   }
		});
		refreshMenu.setCommand(new Command() {
			public void execute() {
				refreshTable();
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
	
	private void refreshTable() {
		LibreRPCService.getProjectList(ClientSession.getInstance().getSessionId(),
				new AsyncCallback<List<Project>>() {
				public void onFailure(Throwable caught) {
					TableView.registerErrorDialog(new StackTrace(caught));
				}
				@Override
				public void onSuccess(List<Project> result) {
					if(result != null) {
						projectList = result;
						populateTable(projectList);
					}
				}
		});
	}
	
	private void populateTable(List<Project> projects) {
		ListDataProvider<Project> dataProvider = new ListDataProvider<Project>();
		
		dataProvider.addDataDisplay(table);
		
		List<Project> list = dataProvider.getList();
		for (Project p : projects) {
	    	list.add(p);
		}
	}
	
	void myRemove() {
		this.removeFromParent();
	}

}
