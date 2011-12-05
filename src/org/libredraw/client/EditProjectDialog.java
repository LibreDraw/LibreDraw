package org.libredraw.client;

import java.util.List;

import org.libredraw.shared.PermissionRecord;
import org.libredraw.shared.Project;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class EditProjectDialog extends DialogBox {
	
	private final LibreRPCAsync LibreRPCService = GWT
			.create(LibreRPC.class);

	private static EditProjectDialogUiBinder uiBinder = GWT
			.create(EditProjectDialogUiBinder.class);
	@UiField TextBox nameTextBox;
	@UiField(provided=true) CellTable<PermissionRecord> table = new CellTable<PermissionRecord>();
	@UiField TextBox newUserTextBox;
	@UiField Button addUserButton;
	@UiField Button submitButton;
	@UiField Button cancelButton;
	Project thisProject;
	List<PermissionRecord> permissions;

	interface EditProjectDialogUiBinder extends
			UiBinder<Widget, EditProjectDialog> {
	}

	public EditProjectDialog(Project p) {
		setWidget(uiBinder.createAndBindUi(this));
		thisProject = p;
		
		this.setText("Edit Project");
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
		
		nameTextBox.setText(thisProject.m_name);
		
		refresh();
		
		this.center();
	}
	
	public void refresh() {
		LibreRPCService.getPermissionsForProject(ClientSession.getInstance().getSessionId(), 
				thisProject.id, new AsyncCallback<List<PermissionRecord>>() {
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
		LibreRPCService.putPermissionsProject(ClientSession.getInstance().getSessionId(),
				thisProject.id, 
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
						ProjectList.getInstance().refresh();
					}
		});
	}
	
	@UiHandler("submitButton")

	void onSubmitButtonClick(ClickEvent event) {
		if(!nameTextBox.getText().equals(thisProject.m_name)) {
			LibreRPCService.changeNameProject(ClientSession.getInstance().getSessionId(),
					thisProject.id,
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
