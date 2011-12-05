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
import java.util.logging.Level;
import java.util.logging.Logger;

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
import com.google.gwt.user.client.Window;
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
	
	private static Logger logger = Logger.getLogger("NameOfYourLogger");
	
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
		
		this.setText("Edit UML Assocation");
		
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
				logger.log(Level.WARNING, result.m_name);
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
		
		for(int i = 0; i< entityOneComboBox.getItemCount(); i++) {
			if(thisAssociation.left.m_name.equals(entityOneComboBox.getItemText(i)))
				entityOneComboBox.setSelectedIndex(i);
		}
		

		for(int i = 0; i< entityTwoComboBox.getItemCount(); i++) {
			if(thisAssociation.right.m_name.equals(entityTwoComboBox.getItemText(i)))
				entityTwoComboBox.setSelectedIndex(i);
		}

		
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
	
	@UiHandler("submitButton")
	void onSubmitButtonClick(ClickEvent event) {
		DiagramEntity one = null, two = null;
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
				one = d;
		}
		String twoName = entityTwoComboBox.getValue(entityTwoComboBox.getSelectedIndex());
		for(DiagramEntity d : entities) {
			if(d.m_name.equals(twoName))
				two = d;
		}
		if(one == null || two == null) {
			//TODO error
		} else {
			logger.log(Level.WARNING, one.toString());
			logger.log(Level.WARNING, two.toString());
			thisAssociation.m_name = nameTextBox.getText();
			thisAssociation.left =  one;
			thisAssociation.right = two;
			thisAssociation.m_leftName = entityOneNameTextBox.getText();
			thisAssociation.m_leftMultiplicity = entityOneMultiplicityTextBox.getText();
			thisAssociation.m_rightName = entityTwoNameTextBox.getText();
			thisAssociation.m_rightMultiplicity = entityTwoMultiplicityTextBox.getText();
			thisAssociation.m_type = getType(typeComboBox.getValue(typeComboBox.getSelectedIndex()));
			
			
			LibreRPCService.updateUMLAssociation(
					ClientSession.getInstance().getSessionId(),
					thisBranch, 
					thisAssociation, 
					new AsyncCallback<Boolean>() {
						@Override
						public void onFailure(Throwable caught) {
							TableView.registerDialog(new StackTrace(caught));
						}
						@Override
						public void onSuccess(Boolean result) {
							if(result)
							{
								myHide();
								DiagramView.getInstance().refresh();
							}
							
						}
			});
		}
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
		LibreRPCService.unlock(ClientSession.getInstance().getSessionId(), 
				thisAssociation.entityKey, 
				new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {
				TableView.registerErrorDialog(new StackTrace(caught));
			}
			@Override
			public void onSuccess(Boolean result) {
				if(result)
					myHide();
				else
					Window.alert("Generic error.");
			}
		});
	}
	
	private void myHide() {
		this.hide();
		this.removeFromParent();
	}

}
