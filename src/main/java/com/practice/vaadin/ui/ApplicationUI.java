package com.practice.vaadin.ui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.practice.vaadin.data.services.IBackend;
import com.practice.vaadin.ui.main.Person;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI
public class ApplicationUI extends UI {
	private static final long serialVersionUID = 1L;

	private GridLayout form;
	private Table table;
	private HorizontalLayout tableControls;
	private HorizontalLayout formControls;
	
	@Autowired
	private IBackend backend;
	private FieldGroup fieldGroup = new FieldGroup();
	
	@Override
	protected void init(VaadinRequest request) {
		
		VerticalLayout  mainLayout = new VerticalLayout();
		mainLayout.setSpacing(true);
		mainLayout.setMargin(true);

		mainLayout.addComponent(buildTableControls());
		mainLayout.addComponent(buildTable());
		mainLayout.addComponent(buildForm());
		mainLayout.addComponent(buildFormControls());
		setContent(mainLayout);
	}
	
	@SuppressWarnings("serial")
	private Component buildTableControls() {
	    tableControls = new HorizontalLayout();
	    Button add = new Button("Add", new Button.ClickListener() {
	        public void buttonClick(ClickEvent event) {
	            editPerson(new Person());
	        }
	    });
	    
	    Button delete = new Button("Delete", new Button.ClickListener() {
	        public void buttonClick(ClickEvent event) {
	            backend.deletePerson((Person) table.getValue());
	            updateTableData();
	        }
	    });
	    
	    tableControls.addComponent(add);
	    tableControls.addComponent(delete);
	    return tableControls;
	}

	private Component buildTable(){
		table = new Table(null);
		table.setWidth(500F, Unit.PIXELS);
		table.setSelectable(true);
		table.setImmediate(true);
		
		table.addValueChangeListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				editPerson((Person)table.getValue());
			}
		});
		updateTableData();
		return table;
	}
	
	private void editPerson(Person person) {
	    if (person == null) {
	        person = new Person();
	    }
	    BeanItem<Person> item = new BeanItem<Person>(person);
	    fieldGroup.setItemDataSource(item);
	}
	
	private void updateTableData() {
		List<Person> persons = backend.getPersons();
		BeanItemContainer<Person> container = new BeanItemContainer<Person>(
				Person.class, persons);
		table.setContainerDataSource(container);

		table.setVisibleColumns("firstName", "lastName", "phoneNumber", "email", "dateOfBirth");
		table.setColumnHeaders("First name", "Last name", "Phone number", "E-mail address", "Date of birth");
		table.sort(new Object[] { "firstName", "lastName" }, new boolean[] {true, true });
	}
	
	@SuppressWarnings({"serial", "unchecked"})
	private Component buildFormControls() {
	    formControls = new HorizontalLayout();
	    Button save = new Button("Save", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {	
				try{
					fieldGroup.commit();
					backend.storePerson(((BeanItem<Person>)fieldGroup.getItemDataSource()).getBean());
					updateTableData();
					editPerson(null);
				}
				catch(CommitException e){
					e.printStackTrace();
				}
			}
		});
	    
	    Button discard = new Button("Discard", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				fieldGroup.discard();
			}
		});
	    
	    formControls.addComponent(save);
	    formControls.addComponent(discard);
	    return formControls;
	}
	
	private Component buildForm() {
        form = new GridLayout(2, 3);

        TextField firstName = new TextField("First name:");
        TextField lastName = new TextField("Last name:");
        TextField phoneNumber = new TextField("Phone Number:");
        TextField email = new TextField("E-mail address:");
        DateField dateOfBirth = new DateField("Date of birth:");
        TextArea comments = new TextArea("Comments:");

        fieldGroup.bind(firstName, "firstName");
        fieldGroup.bind(lastName, "lastName");
        fieldGroup.bind(phoneNumber, "phoneNumber");
        fieldGroup.bind(email, "email");
        fieldGroup.bind(dateOfBirth, "dateOfBirth");
        fieldGroup.bind(comments, "comments");
        
        form.addComponent(firstName);
        form.addComponent(lastName);
        form.addComponent(phoneNumber);
        form.addComponent(email);
        form.addComponent(dateOfBirth);
        form.addComponent(comments);
        return form;
    }
}
