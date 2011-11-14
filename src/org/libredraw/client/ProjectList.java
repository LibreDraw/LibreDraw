package org.libredraw.client;

import java.util.Date;
import java.util.List;
import java.util.Arrays;
import org.libredraw.shared.LDUser;
import org.libredraw.shared.Project;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ListDataProvider;

public class ProjectList extends Composite {

	private static ProjectListUiBinder uiBinder = GWT
			.create(ProjectListUiBinder.class);
	@UiField(provided=true) CellTable<Project> table = new CellTable<Project>();

	interface ProjectListUiBinder extends UiBinder<Widget, ProjectList> {
	}
	
	public ProjectList() {
		initWidget(uiBinder.createAndBindUi(this));
		
		List<Project> ps = Arrays.asList(
				new Project("LibreDraw", new Date(), new LDUser("Day9"), new Date(), new LDUser("Day9")),
				new Project("ManOfSteal", new Date(), new LDUser("Rick"), new Date(), new LDUser("Tyson")));
		
		Column<Project, Boolean> checkColumn = new Column<Project, Boolean>(
			new CheckboxCell(true, false)) {
				@Override
				public Boolean getValue(Project p) {
					return null;
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
		
		ListDataProvider<Project> dataProvider = new ListDataProvider<Project>();
		
		dataProvider.addDataDisplay(table);
		
		List<Project> list = dataProvider.getList();
		for (Project contact : ps) {
	    	list.add(contact);
		}
			
			
			
	}

}
