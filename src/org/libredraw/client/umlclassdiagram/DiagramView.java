package org.libredraw.client.umlclassdiagram;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.libredraw.client.ClientSession;
import org.libredraw.client.LibreRPC;
import org.libredraw.client.LibreRPCAsync;
import org.libredraw.client.StackTrace;
import org.libredraw.client.TableView;
import org.libredraw.shared.DiagramEntity;
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
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.user.client.ui.TabPanel;

public class DiagramView extends Composite {
	
	private final LibreRPCAsync LibreRPCService = GWT
			.create(LibreRPC.class);
	
	private static Logger logger = Logger.getLogger("DiagramView.java");

	private static DiagramViewUiBinder uiBinder = GWT
			.create(DiagramViewUiBinder.class);
	@UiField(provided=true) CellTable<DiagramEntity> table = new CellTable<DiagramEntity>();
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
	@UiField
	static ScrollPanel scrollPanel;
	@UiField static TabPanel tabPanel;
	long thisBranch;

	interface DiagramViewUiBinder extends UiBinder<Widget, DiagramView> {
	}

	public DiagramView(long branch) {
		initWidget(uiBinder.createAndBindUi(this));
		
		thisBranch = branch;
		
		tabPanel.selectTab(0);
		
		onResize();
		
		Column<DiagramEntity, Boolean> checkColumn = new Column<DiagramEntity, Boolean>( 
				new CheckboxCell(true, false)) {
					@Override
					public Boolean getValue(DiagramEntity d) {
						return false;
					}
			};
			
		TextColumn<DiagramEntity> nameColumn = new TextColumn<DiagramEntity>() {
			@Override
			public String getValue(DiagramEntity d) {
				return d.m_name;
			}
		};
		
		TextColumn<DiagramEntity> typeColumn = new TextColumn<DiagramEntity>() {
			@Override
			public String getValue(DiagramEntity d) {
				return d.getClass().getName();
			}
		};
				
		TextColumn<DiagramEntity> modifiedColumn = new TextColumn<DiagramEntity>() {
			@Override
			public String getValue(DiagramEntity d) {
				return d.m_modified.toString();
			}
		};
				
		TextColumn<DiagramEntity> modifiedByColumn = new TextColumn<DiagramEntity>() {
			@Override
			public String getValue(DiagramEntity d) {
				return d.createdBy.m_displayName;
			}
		};
		
		TextColumn<DiagramEntity> createdOnColumn = new TextColumn<DiagramEntity>() {
			@Override
			public String getValue(DiagramEntity d) {
				return d.m_created.toString();
			}
		};
				
		TextColumn<DiagramEntity> ownerColumn = new TextColumn<DiagramEntity>() {
			@Override
			public String getValue(DiagramEntity d) {
				return d.createdBy.m_displayName;
			}
		};
		
		table.addColumn(checkColumn, "");
		table.addColumn(nameColumn, "Name");
		table.addColumn(typeColumn, "Type");
		table.addColumn(modifiedColumn, "Date Modified");
		table.addColumn(modifiedByColumn, "By");
		table.addColumn(createdOnColumn, "Created On");
		table.addColumn(ownerColumn, "Created By");
		
		table.setWidth("100%", true);
		table.setColumnWidth(checkColumn, 50.0, Unit.PX);
		
		Window.addResizeHandler(new ResizeHandler() {
			public void onResize(ResizeEvent event) {
				DiagramView.onResize();
			}
		});

		newClassMenu.setCommand(new Command() {
			@Override
			public void execute() {
				TableView.registerDialog(new newClassDialog(thisBranch));
			}
		});
		
		refreshMenu.setCommand(new Command() {
			@Override
			public void execute() {
				refresh();
			}
			
		});
		
		refresh();
	}

	private static void onResize() {
		//Set height of scrollPanel widget window height - header - footer
		Integer windowHeight = Window.getClientHeight();
		Integer windowWidth = Window.getClientWidth();
		tabPanel.setHeight((windowHeight-150)+"px");
		tabPanel.setWidth((windowWidth-2)+"px");
		scrollPanel.setHeight((windowHeight-80)+"px");
	}
	
	private void refresh() {
		LibreRPCService.getEntities(ClientSession.getInstance().getSessionId(),
				thisBranch, 
				new AsyncCallback<List<DiagramEntity>>() {
				public void onFailure(Throwable caught) {
					TableView.registerErrorDialog(new StackTrace(caught));
				}
				@Override
				public void onSuccess(List<DiagramEntity> result) {
					if(result != null) {
						populateTable(result);
					}
				}
		});
	}
	
	private void populateTable(List<DiagramEntity> entities) {
		ListDataProvider<DiagramEntity> dataProvider = new ListDataProvider<DiagramEntity>();
		
		dataProvider.addDataDisplay(table);
		
		List<DiagramEntity> list = dataProvider.getList();
		for (DiagramEntity e : entities) {
			list.add(e);
			logger.log(Level.SEVERE,"Adding: " + e.getClass());
		}
	}
	
	public void myRemove() {
		this.removeFromParent();
	}
}
