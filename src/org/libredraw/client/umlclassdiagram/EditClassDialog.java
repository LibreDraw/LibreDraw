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

import java.util.Vector;
import org.libredraw.client.ClientSession;
import org.libredraw.client.LibreRPC;
import org.libredraw.client.LibreRPCAsync;
import org.libredraw.client.StackTrace;
import org.libredraw.client.TableView;
import org.libredraw.shared.umlclassdiagram.UMLAttribute;
import org.libredraw.shared.umlclassdiagram.UMLAttributeParser;
import org.libredraw.shared.umlclassdiagram.UMLClass;
import org.libredraw.shared.umlclassdiagram.UMLOperation;
import org.libredraw.shared.umlclassdiagram.UMLOperationParser;
import org.libredraw.shared.umlclassdiagram.UMLVisibility;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.CheckBox;
import com.googlecode.objectify.Key;

@SuppressWarnings("deprecation")
public class EditClassDialog extends DialogBox {
	
	private final LibreRPCAsync LibreRPCService = GWT
			.create(LibreRPC.class);

	private static newClassDialogUiBinder uiBinder = GWT
			.create(newClassDialogUiBinder.class);
	@UiField Button attributeAddButton;
	@UiField TextBox atributeTextBox;
	@UiField TextBox nameTextBox;
	@UiField Button newOperationButton;
	@UiField TextBox operationsTextBox;
	@UiField VerticalPanel attributePanel;
	@UiField VerticalPanel operationPanel;
	@UiField Label errorLabel;
	@UiField CheckBox abstractCheckBox;
	Vector<TextBox> attributeTexts;
	Vector<TextBox> operationTexts;
	long thisBranch;
	UMLClass thisClass;

	private final class ClickListenerImpl implements ClickListener {
		TextBox m_textBox;
		public ClickListenerImpl(TextBox b) {
			m_textBox = b;
		}

		@Override
		public void onClick(Widget sender) {
			HorizontalPanel h = (HorizontalPanel) sender.getParent();
			h.removeFromParent();
			if(attributeTexts.contains(m_textBox))
				attributeTexts.remove(m_textBox);
			if(operationTexts.contains(m_textBox))
				operationTexts.remove(m_textBox);
		}
	}
	
	interface newClassDialogUiBinder extends UiBinder<Widget, EditClassDialog> {
	}

	public EditClassDialog(Key<?> entityKey, long branch) {
		setWidget(uiBinder.createAndBindUi(this));
		this.setAnimationEnabled(true);
		this.setAnimationEnabled(true);
		this.setGlassEnabled(true);
		this.center();
		
		this.setText("Edit UML Class");
		
		attributeTexts = new Vector<TextBox>();
		attributeTexts.add(atributeTextBox);
		
		operationTexts = new Vector<TextBox>();
		operationTexts.add(operationsTextBox);
		thisBranch = branch;
		
		LibreRPCService.getUMLClass(ClientSession.getInstance().getSessionId(), 
				entityKey.getId(), new AsyncCallback<UMLClass>() {
			@Override
			public void onFailure(Throwable caught) {
				TableView.registerErrorDialog(new StackTrace(caught));
			}
			@Override
			public void onSuccess(UMLClass result) {
				thisClass = result;
				populateBoxes();
			}
		});
	}
	
	private void populateBoxes() {
		int count = 0;
		for(UMLAttribute a:thisClass.attributes) {
			if(count == 0)
				atributeTextBox.setText(a.toString());
			else
				addNewAttributeBox(a.toString());
			count++;
		}
		count = 0;
		for(UMLOperation o:thisClass.operations) {
			if(count == 0)
				operationsTextBox.setText(o.toString());
			else
				addNewOperationBox(o.toString());
			count++;
		}
		nameTextBox.setText(thisClass.m_name);
		abstractCheckBox.setValue(thisClass.m_abstract);
		this.center();
	}

	@UiHandler("attributeAddButton")
	void onAttributeAddButtonClick(ClickEvent event) {
		addNewAttributeBox("");
	}
	
	private void addNewAttributeBox(String string) {
		HorizontalPanel h = new HorizontalPanel();
		AbsolutePanel spacer = new AbsolutePanel();
		spacer.setWidth("94px");
		h.add(spacer);
		TextBox text = new TextBox();
		text.setHeight("16px");
		text.setWidth("170px");
		text.setText(string);
		attributeTexts.add(text);
		h.add(text);
		Button remove = new Button("-", new ClickListenerImpl(text));
		remove.setHeight("28px");
		remove.setWidth("28px");
		h.add(remove);
		attributePanel.add(h);
	}

	@UiHandler("newOperationButton")
	void onNewOperationButtonClick(ClickEvent event) {
		addNewOperationBox("");
	}
	
	private void addNewOperationBox(String string) {
		HorizontalPanel h = new HorizontalPanel();
		AbsolutePanel spacer = new AbsolutePanel();
		spacer.setWidth("94px");
		h.add(spacer);
		TextBox text = new TextBox();
		text.setHeight("16px");
		text.setWidth("170px");
		operationTexts.add(text);
		h.add(text);
		Button remove = new Button("-", new ClickListenerImpl(text));
		remove.setHeight("28px");
		remove.setWidth("28px");
		h.add(remove);
		operationPanel.add(h);
	}

	@UiHandler("cancelButton")
	void onCancelButtonClick(ClickEvent event) {
		LibreRPCService.unlock(ClientSession.getInstance().getSessionId(), 
				thisClass.entityKey, 
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
	
	@UiHandler("submitButton")
	void onSubmitButtonClick(ClickEvent event) {
		errorLabel.setText("");
		Vector<UMLAttribute> attributes = new Vector<UMLAttribute>();
		for(TextBox t : attributeTexts) {
			if(t!=null) {
				UMLAttributeParser parser;
				try {
					parser = new UMLAttributeParser(t.getText());
					attributes.add(parser.getValue());
				} catch (Exception e) {
					if(t.getText() != "") {
						errorLabel.setText("Error parsing: "+ t.getText());
						return;
					}
				}
			}
		}
		
		Vector<UMLOperation> operations = new Vector<UMLOperation>();
		for(TextBox t : operationTexts) {
			if(t!=null) {
				UMLOperationParser parser;
				try {
					parser = new UMLOperationParser(t.getText());
					operations.add(parser.getValue());
				} catch (Exception e) {
					if(t.getText() != "") {
						errorLabel.setText("Error parsing: "+ t.getText());
						return;
					}
				}
			}
		}
		
		thisClass.m_name = nameTextBox.getText();
		thisClass.m_visibility = UMLVisibility.Public;
		thisClass.m_abstract = abstractCheckBox.getValue();
		thisClass.operations = operations;
		thisClass.attributes = attributes;
		LibreRPCService.updateUMLClass(ClientSession.getInstance().getSessionId(), 
				thisBranch, thisClass, new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {
				TableView.registerErrorDialog(new StackTrace(caught));
			}
			@Override
			public void onSuccess(Boolean result) {
				if(result) {
					myHide();
					DiagramView.getInstance().refresh();
				}
			}
		});
	}
	
	void myHide() {
		this.removeFromParent();
	}
	
}
