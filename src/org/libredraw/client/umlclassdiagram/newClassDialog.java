package org.libredraw.client.umlclassdiagram;

import java.util.Vector;

import org.libredraw.client.LibreRPC;
import org.libredraw.client.LibreRPCAsync;
import org.libredraw.shared.umlclassdiagram.UMLAttribute;
import org.libredraw.shared.umlclassdiagram.UMLAttributeParser;
import org.libredraw.shared.umlclassdiagram.UMLClass;
import org.libredraw.shared.umlclassdiagram.UMLOperation;
import org.libredraw.shared.umlclassdiagram.UMLVisibility;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
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

@SuppressWarnings("deprecation")
public class newClassDialog extends DialogBox {
	
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
	ClickListenerImpl removeButtonListener;
	Vector<TextBox> attributeTexts;
	Vector<TextBox> operationTexts;
	long thisBranch;

	private final class ClickListenerImpl implements ClickListener {
		@Override
		public void onClick(Widget sender) {
			HorizontalPanel h = (HorizontalPanel) sender.getParent();
			h.removeFromParent();
		}
	}
	
	interface newClassDialogUiBinder extends UiBinder<Widget, newClassDialog> {
	}

	public newClassDialog(long branch) {
		setWidget(uiBinder.createAndBindUi(this));
		this.setAnimationEnabled(true);
		this.setAnimationEnabled(true);
		this.setGlassEnabled(true);
		this.center();
		
		this.setText("New UML Class");
		
		removeButtonListener = new ClickListenerImpl();
		
		attributeTexts = new Vector<TextBox>();
		attributeTexts.add(atributeTextBox);
		
		operationTexts = new Vector<TextBox>();
		operationTexts.add(operationsTextBox);
	}

	@UiHandler("attributeAddButton")
	void onAttributeAddButtonClick(ClickEvent event) {
		HorizontalPanel h = new HorizontalPanel();
		AbsolutePanel spacer = new AbsolutePanel();
		spacer.setWidth("94px");
		h.add(spacer);
		TextBox text = new TextBox();
		text.setHeight("16px");
		text.setWidth("170px");
		attributeTexts.add(text);
		h.add(text);
		Button remove = new Button("-", removeButtonListener);
		remove.setHeight("28px");
		remove.setWidth("28px");
		h.add(remove);
		attributePanel.add(h);
	}
	
	@UiHandler("newOperationButton")
	void onNewOperationButtonClick(ClickEvent event) {
		HorizontalPanel h = new HorizontalPanel();
		AbsolutePanel spacer = new AbsolutePanel();
		spacer.setWidth("94px");
		h.add(spacer);
		TextBox text = new TextBox();
		text.setHeight("16px");
		text.setWidth("170px");
		operationTexts.add(text);
		h.add(text);
		Button remove = new Button("-", removeButtonListener);
		remove.setHeight("28px");
		remove.setWidth("28px");
		h.add(remove);
		operationPanel.add(h);
	}
	
	@UiHandler("cancelButton")
	void onCancelButtonClick(ClickEvent event) {
		this.removeFromParent();
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
		
		UMLClass thisClass = new UMLClass(nameTextBox.getText(), UMLVisibility.Public, abstractCheckBox.getValue(), operations, attributes);
	}
}
