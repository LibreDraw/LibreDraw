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

package org.libredraw.client.umlclassdiagram;

import java.util.Date;
import java.util.List;
import org.libredraw.client.ClientSession;
import org.libredraw.client.LibreRPC;
import org.libredraw.client.LibreRPCAsync;
import org.libredraw.client.StackTrace;
import org.libredraw.client.TableView;
import org.libredraw.shared.DiagramEntity;
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
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.user.client.ui.TabPanel;

public class DiagramView extends Composite {
	
	private final LibreRPCAsync LibreRPCService = GWT
			.create(LibreRPC.class);

	private static DiagramViewUiBinder uiBinder = GWT
			.create(DiagramViewUiBinder.class);
	@UiField(provided=true) CellTable<DiagramEntity> table = new CellTable<DiagramEntity>();
	@UiField MenuItem newEnumerationMenu;
	@UiField MenuItem newInterfaceMenu;
	@UiField MenuItem newClassMenu;
	@UiField MenuItem refreshMenu;
	@UiField MenuItem newAssociationMenu;
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
	List<DiagramEntity> entities;
	
	Date clickTracker = null;
	
	static DiagramView instance = null;

	interface DiagramViewUiBinder extends UiBinder<Widget, DiagramView> {
	}
	
	public static DiagramView getInstance() {
		if(instance == null)
			instance = new DiagramView();
		return instance;
	}
	
	public void setBranch(long branch) {
		thisBranch = branch;
		this.refresh();
	}

	private DiagramView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		
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
				return d.entityKey.getKind();
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
		
		newInterfaceMenu.setCommand(new Command() {
			@Override
			public void execute() {
				TableView.registerDialog(new newInterfaceDialog(thisBranch));
			}	
		});
		
		newAssociationMenu.setCommand(new Command() {
			@Override
			public void execute() {
				TableView.registerDialog(new newAssociationDialog(thisBranch, entities));
			}
		});
		
		refreshMenu.setCommand(new Command() {
			@Override
			public void execute() {
				refresh();
			}
		});
		
		table.addCellPreviewHandler(new CellPreviewEvent.Handler<DiagramEntity>() {
			@Override
			public void onCellPreview(CellPreviewEvent<DiagramEntity> event) {
				//TODO get a real double click event
				if("click".equals(event.getNativeEvent().getType())) {
					if(event.getContext().getColumn() != 0) {
						if(clickTracker == null)
							clickTracker = new Date();
						else {
							long difference = new Date().getTime() - clickTracker.getTime();
							if(difference <= 500){
								DiagramEntity e = event.getValue();
								if("UMLClass".equals(e.entityKey.getKind())) {
									TableView.registerDialog(new editClassDialog(e.entityKey, thisBranch));
								} else if("UMLInterface".equals(e.entityKey.getKind())) {
									
								} else if("UMLAssociation".equals(e.entityKey.getKind())) {
									
								} else { // should never happen
									Window.alert("generic error");
								}
							}
							else
								clickTracker = new Date();
						}
					}
				}
			}
		});
		
	}

	private static void onResize() {
		//Set height of scrollPanel widget window height - header - footer
		Integer windowHeight = Window.getClientHeight();
		Integer windowWidth = Window.getClientWidth();
		tabPanel.setHeight((windowHeight-150)+"px");
		tabPanel.setWidth((windowWidth-2)+"px");
		scrollPanel.setHeight((windowHeight-80)+"px");
	}
	
	public void refresh() {
		LibreRPCService.getEntities(ClientSession.getInstance().getSessionId(),
				thisBranch, 
				new AsyncCallback<List<DiagramEntity>>() {
				public void onFailure(Throwable caught) {
					TableView.registerErrorDialog(new StackTrace(caught));
				}
				@Override
				public void onSuccess(List<DiagramEntity> result) {
					entities = result;
					populateTable(result);
				}
		});
	}
	
	private void populateTable(List<DiagramEntity> entities) {
		ListDataProvider<DiagramEntity> dataProvider = new ListDataProvider<DiagramEntity>();
		
		dataProvider.addDataDisplay(table);
		
		List<DiagramEntity> list = dataProvider.getList();
		for (DiagramEntity e : entities) {
			list.add(e);
		}
	}
	
	public void myRemove() {
		this.removeFromParent();
	}
}
