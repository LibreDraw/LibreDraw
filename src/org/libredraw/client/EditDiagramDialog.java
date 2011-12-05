package org.libredraw.client;

import java.util.List;

import org.libredraw.shared.Diagram;
import org.libredraw.shared.PermissionRecord;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.event.dom.client.ClickEvent;

public class EditDiagramDialog extends DialogBox {
	
	private final LibreRPCAsync LibreRPCService = GWT
			.create(LibreRPC.class);

	private static EditDiagramDialogUiBinder uiBinder = GWT
			.create(EditDiagramDialogUiBinder.class);
	@UiField TextBox nameTextBox;
	@UiField TextBox newUserTextBox;
	@UiField Button addUserButton;
	@UiField(provided=true) CellTable<PermissionRecord> table = new CellTable<PermissionRecord>();
	@UiField Button submitButton;
	@UiField Button cancelButton;
	Diagram thisDiagram;
	List<PermissionRecord> permissions;

	interface EditDiagramDialogUiBinder extends
			UiBinder<Widget, EditDiagramDialog> {
	}

	public EditDiagramDialog(Diagram diagram) {
		setWidget(uiBinder.createAndBindUi(this));
		thisDiagram = diagram;
		
		this.setText("Edit Diagram");
		this.setAnimationEnabled(true);
		this.setAnimationEnabled(true);
		this.setGlassEnabled(true);
		
		TextColumn<PermissionRecord> nameColumn = new TextColumn<PermissionRecord>() {
			@Override
			public String getValue(PermissionRecord r) {
				return r.m_user.m_displayName;
			}
		};
		
		Column<PermissionRecord, Boolean> readColumn = new Column<PermissionRecord, Boolean>( 
				new CheckboxCell(true, false)) {
					@Override
					public Boolean getValue(PermissionRecord object) {
						return object.READ;
					}
		};
		
		Column<PermissionRecord, Boolean> writeColumn = new Column<PermissionRecord, Boolean>( 
				new CheckboxCell(true, false)) {
					@Override
					public Boolean getValue(PermissionRecord object) {
						return object.WRITE;
					}
		};
		
		
		Column<PermissionRecord, Boolean> exportColumn = new Column<PermissionRecord, Boolean>( 
				new CheckboxCell(true, false)) {
					@Override
					public Boolean getValue(PermissionRecord object) {
						return object.EXPORT;
					}
		};
		
		table.addColumn(nameColumn, "User");
		table.addColumn(readColumn, "Read");
		table.addColumn(writeColumn, "Write");
		table.addColumn(exportColumn, "Export");
		
		table.setColumnWidth(nameColumn, 225.0, Unit.PX);
		table.setColumnWidth(readColumn, 25.0, Unit.PX);
		table.setColumnWidth(writeColumn, 25.0, Unit.PX);
		table.setColumnWidth(exportColumn, 25.0, Unit.PX);
		
		nameTextBox.setText(thisDiagram.m_name);
		
		refresh();
		
		this.center();
	}

	public void refresh() {
		LibreRPCService.getPermissionsForDiagram(ClientSession.getInstance().getSessionId(), 
				thisDiagram.id, new AsyncCallback<List<PermissionRecord>>() {
			@Override
			public void onFailure(Throwable caught) {
				TableView.registerErrorDialog(new StackTrace(caught));
			}
			@Override
			public void onSuccess(List<PermissionRecord> result) {
				permissions = result;
				populateTable(permissions);
			}
		});
	}
	
	private void populateTable(List<PermissionRecord> permissions) {
		ListDataProvider<PermissionRecord> dataProvider = new ListDataProvider<PermissionRecord>();
		
		dataProvider.addDataDisplay(table);
		
		List<PermissionRecord> list = dataProvider.getList();
		for (PermissionRecord p : permissions) {
			list.add(p);
		}
	}
	

	@UiHandler("addUserButton")
	void onAddUserButtonClick(ClickEvent event) {
		LibreRPCService.userExists(ClientSession.getInstance().getSessionId(), 
				newUserTextBox.getText(), new AsyncCallback<Boolean>() {
					@Override
					public void onFailure(Throwable caught) {
						TableView.registerErrorDialog(new StackTrace(caught));
					}
					@Override
					public void onSuccess(Boolean result) {
						if(result) {
							permissions.add(new PermissionRecord());
							populateTable(permissions);
						} else {
							newUserTextBox.setText("That user does not exist");
						}
							
					}
		});
	}
	
	private void pushPermissions() {
		LibreRPCService.putPermissionsDiagram(ClientSession.getInstance().getSessionId(),
				thisDiagram.id, 
				permissions, 
				new AsyncCallback<Boolean>() {
					@Override
					public void onFailure(Throwable caught) {
						TableView.registerErrorDialog(new StackTrace(caught));
					}
					@Override
					public void onSuccess(Boolean result) {
						if(!result)
							Window.alert("Generic error.");
						myHide();
						DiagramList.getInstance().refresh();
					}
		});
	}
	
	@UiHandler("submitButton")
	void onSubmitButtonClick(ClickEvent event) {
		if(!nameTextBox.getText().equals(thisDiagram.m_name)) {
			LibreRPCService.changeNameDiagram(ClientSession.getInstance().getSessionId(),
					thisDiagram.id,
					nameTextBox.getText(), 
					new AsyncCallback<Boolean>() {
						@Override
						public void onFailure(Throwable caught) {
							TableView.registerErrorDialog(new StackTrace(caught));
						}
						@Override
						public void onSuccess(Boolean result) {
							if(!result)
								Window.alert("Generic error.");
							pushPermissions();
						
						}
			});
		} else 
			pushPermissions();
	}
	@UiHandler("cancelButton")
	void onCancelButtonClick(ClickEvent event) {
		myHide();
	}
	
	void myHide() {
		this.removeFromParent();
	}
}
