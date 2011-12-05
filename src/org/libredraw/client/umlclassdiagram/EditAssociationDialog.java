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

import java.util.List;
import org.libredraw.client.ClientSession;
import org.libredraw.client.LibreRPC;
import org.libredraw.client.LibreRPCAsync;
import org.libredraw.client.StackTrace;
import org.libredraw.client.TableView;
import org.libredraw.shared.DiagramEntity;
import org.libredraw.shared.umlclassdiagram.UMLAssociation;
import org.libredraw.shared.umlclassdiagram.UMLAssociationType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.googlecode.objectify.Key;

public class EditAssociationDialog extends DialogBox {
	
	private final LibreRPCAsync LibreRPCService = GWT
			.create(LibreRPC.class);

	private static newAssociationDialogUiBinder uiBinder = GWT
			.create(newAssociationDialogUiBinder.class);
	@UiField ListBox entityOneComboBox;
	@UiField ListBox entityTwoComboBox;
	@UiField TextBox nameTextBox;
	@UiField TextBox entityOneNameTextBox;
	@UiField TextBox entityOneMultiplicityTextBox;
	@UiField TextBox entityTwoNameTextBox;
	@UiField TextBox entityTwoMultiplicityTextBox;
	@UiField ListBox typeComboBox;
	long thisBranch;
	List<DiagramEntity> entities;
	UMLAssociation thisAssociation;

	interface newAssociationDialogUiBinder extends
			UiBinder<Widget, EditAssociationDialog> {
	}

	public EditAssociationDialog(long branch, List<DiagramEntity> entities, Key<?> entity) {
		setWidget(uiBinder.createAndBindUi(this));
		
		this.setAnimationEnabled(true);
		this.setAnimationEnabled(true);
		this.setGlassEnabled(true);
		this.center();
		
		this.setText("New UML Assocation");
		
		thisBranch = branch;
		
		entityOneComboBox.addItem("Select and entity:");
		entityTwoComboBox.addItem("Select and entity:");
		if(entities.isEmpty()) {
			entityOneComboBox.addItem("None");
			entityTwoComboBox.addItem("None");
		} else {
			for(DiagramEntity d : entities) {
				if(!"UMLAssociation".equals(d.entityKey.getKind())) {
					entityOneComboBox.addItem(d.m_name);
					entityTwoComboBox.addItem(d.m_name);
				}
			}
		}

		typeComboBox.addItem("Select a type:");
		typeComboBox.addItem("Association");
		typeComboBox.addItem("Aggregation");
		typeComboBox.addItem("Composition");
		typeComboBox.addItem("Dependency");
		typeComboBox.addItem("Inheritance");
		this.entities = entities;
		
		LibreRPCService.getUMLAssociation(ClientSession.getInstance().getSessionId(), 
				entity.getId(), new AsyncCallback<UMLAssociation>() {
			@Override
			public void onFailure(Throwable caught) {
				TableView.registerErrorDialog(new StackTrace(caught));
			}
			@Override
			public void onSuccess(UMLAssociation result) {
				thisAssociation = result;
				populateBoxes();
			}
		});
		
	}
	
	void populateBoxes() {
		nameTextBox.setText(thisAssociation.m_name);
		entityOneNameTextBox.setText(thisAssociation.m_leftName);
		entityOneMultiplicityTextBox.setText(thisAssociation.m_leftMultiplicity);
		entityTwoNameTextBox.setText(thisAssociation.m_rightName);
		entityTwoMultiplicityTextBox.setText(thisAssociation.m_rightMultiplicity);
		
		int count = 0;
		for(DiagramEntity d:entities) {
			if(d.m_name.equals(thisAssociation.left.m_name))
				break;
			count++;
		}
		entityOneComboBox.setSelectedIndex(count);
		count = 0;
		for(DiagramEntity d:entities) {
			if(d.m_name.equals(thisAssociation.right.m_name))
				break;
			count++;
		}
		entityTwoComboBox.setSelectedIndex(count);
		
		if(thisAssociation.m_type == UMLAssociationType.Association)
			typeComboBox.setSelectedIndex(1);
		if(thisAssociation.m_type == UMLAssociationType.Aggregation)
			typeComboBox.setSelectedIndex(2);
		if(thisAssociation.m_type == UMLAssociationType.Composition)
			typeComboBox.setSelectedIndex(3);
		if(thisAssociation.m_type == UMLAssociationType.Dependency)
			typeComboBox.setSelectedIndex(4);
		if(thisAssociation.m_type == UMLAssociationType.Inheritance)
			typeComboBox.setSelectedIndex(5);
	}
	
	@SuppressWarnings("unchecked")
	@UiHandler("submitButton")
	void onSubmitButtonClick(ClickEvent event) {
		Key<DiagramEntity> one = null, two = null;
		if("Select and entity:".equals(entityOneComboBox.getValue(entityOneComboBox.getSelectedIndex()))) {
			//TODO error
		}
		if("Select and entity:".equals(entityTwoComboBox.getValue(entityTwoComboBox.getSelectedIndex()))) {
			//TODO error
		}
		if("Select a type:".equals(typeComboBox.getValue(typeComboBox.getSelectedIndex()))) {
			//TODO error
		}
		String oneName = entityOneComboBox.getValue(entityOneComboBox.getSelectedIndex());
		for(DiagramEntity d : entities) {
			if(d.m_name.equals(oneName))
				one = (Key<DiagramEntity>) d.entityKey;
		}
		String twoName = entityTwoComboBox.getValue(entityTwoComboBox.getSelectedIndex());
		for(DiagramEntity d : entities) {
			if(d.m_name.equals(twoName))
				two = (Key<DiagramEntity>) d.entityKey;
		}
		if(one == null || two == null) {
			//TODO error
		}
		UMLAssociation a = new UMLAssociation(
				nameTextBox.getText(),
				one,
				two,
				entityOneNameTextBox.getText(),
				entityOneMultiplicityTextBox.getText(),
				entityTwoNameTextBox.getText(),
				entityTwoMultiplicityTextBox.getText(),
				getType(entityTwoComboBox.getValue(entityTwoComboBox.getSelectedIndex())),
				null);
		
		
		LibreRPCService.addAssocation(ClientSession.getInstance().getSessionId(),
				thisBranch, a, new AsyncCallback<String>() {
					@Override
					public void onFailure(Throwable caught) {
						TableView.registerDialog(new StackTrace(caught));
					}
					@Override
					public void onSuccess(String result) {
						if("Sucsess".equals(result))
						{
							myHide();
							DiagramView.getInstance().refresh();
						}
						
					}
		});
	}
	
	private UMLAssociationType getType(String type) {
		if("Association".equals(type))
				return UMLAssociationType.Association;
		if("Aggregation".equals(type))
				return UMLAssociationType.Aggregation;
		if("Composition".equals(type))
				return UMLAssociationType.Composition;
		if("Dependency".equals(type))
				return UMLAssociationType.Dependency;
		if("Inheritance".equals(type))
				return UMLAssociationType.Inheritance;
		return null;
	}

	@UiHandler("cancelButton")
	void onCancelButtonClick(ClickEvent event) {
		myHide();
	}
	
	private void myHide() {
		this.hide();
		this.removeFromParent();
	}

}
