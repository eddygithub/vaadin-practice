package com.practice.vaadin.ui;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;

public class CustomerFormUI extends GridLayout {

	private static final long serialVersionUID = 1L;
	
	public CustomerFormUI(FieldGroup fieldGroup, boolean visible) {
		super(2, 3);
		buildUI(fieldGroup);
		setVisible(visible);
	}
	
	public CustomerFormUI(FieldGroup fieldGroup) {
		this(fieldGroup, true);
	}
	
	private void buildUI(FieldGroup fieldGroup){
		TextField firstName = new TextField("First name:");
		TextField lastName = new TextField("Last name:");
		TextField phoneNumber = new TextField("Phone Number:");
		TextField email = new TextField("E-mail address:");
		DateField dateOfBirth = new DateField("Date of birth:");
		TextArea comments = new TextArea("Comments:");
		Upload taxPDFUpLoad = new Upload("Upload", new Upload.Receiver() {
			private static final long serialVersionUID = 1L;

			@Override
			public OutputStream receiveUpload(String filename, String mimeType) {
				System.out.println(filename);
				OutputStream os=null;
				try{
					os = new FileOutputStream(filename);
				}
				catch(FileNotFoundException f){
					f.printStackTrace();
				}
				return os;
			}
		});
		
		fieldGroup.bind(firstName, "firstName");
		fieldGroup.bind(lastName, "lastName");
		fieldGroup.bind(phoneNumber, "phoneNumber");
		fieldGroup.bind(email, "email");
		fieldGroup.bind(dateOfBirth, "dateOfBirth");
		fieldGroup.bind(comments, "comments");
		
		addComponent(firstName);
		addComponent(lastName);
		addComponent(phoneNumber);
		addComponent(email);
		addComponent(dateOfBirth);
		addComponent(comments);
		addComponent(taxPDFUpLoad);
	}
	
}
