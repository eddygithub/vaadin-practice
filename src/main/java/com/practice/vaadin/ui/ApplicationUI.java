package com.practice.vaadin.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.practice.vaadin.domain.Customer;
import com.practice.vaadin.persistent.services.DataStoreService;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI
public class ApplicationUI extends UI {
	private static final long serialVersionUID = 1L;

	private Table table;
	private HorizontalLayout tableControls;
	private HorizontalLayout formControls;
	
	@Qualifier("mongoDataStore")
	@Autowired
	private DataStoreService dataStore;

	private FieldGroup fieldGroup = new FieldGroup();
	private GridLayout form = new CustomerFormUI(fieldGroup, false);
	private Button delete;
	
	@Override
	protected void init(VaadinRequest request) {
		BrowserFrame browser = new BrowserFrame("Browser",
				new ExternalResource("http://www.prudentcpa.com"));
		browser.setWidth("600px");
		browser.setHeight("400px");

		VerticalLayout  mainLayout = new VerticalLayout();
		mainLayout.addComponent(browser);
		mainLayout.setSpacing(true);
		mainLayout.setMargin(true);

		mainLayout.addComponent(new MenuBarUI());
		mainLayout.addComponent(buildTableControls());
		mainLayout.addComponent(buildTable());
		mainLayout.addComponent(form);
		mainLayout.addComponent(buildFormControls());
		setContent(mainLayout);
	}
	
	@SuppressWarnings("serial")
	private Component buildTableControls() {
	    tableControls = new HorizontalLayout();
	    Button add = new Button("Add", new Button.ClickListener() {
	        public void buttonClick(ClickEvent event) {
	            editPerson(new Customer());
	            form.setVisible(true);
	        }
	    });
	    
	    delete = new Button("Delete", new Button.ClickListener() {
	        public void buttonClick(ClickEvent event) {
	            dataStore.deletePerson((Customer) table.getValue());
	            updateTableData();
	        }
	    });
	    delete.setEnabled(false);
	    tableControls.addComponent(add);
	    tableControls.addComponent(delete);
	    return tableControls;
	}

	private Component buildTable(){
		table = new Table(null);
		table.setSelectable(true);
		table.setImmediate(true);
		table.setSizeFull();
		table.addValueChangeListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				editPerson((Customer)table.getValue());
				delete.setEnabled(true);
				form.setVisible(true);
			}
		});
		
		table.setColumnFooter("firstName", "");
		table.setFooterVisible(true);
		updateTableData();
		return table;
	}
	
	private void editPerson(Customer person) {
	    if (person == null) {
	        person = new Customer();
	    }
	    BeanItem<Customer> item = new BeanItem<Customer>(person);
	    fieldGroup.setItemDataSource(item);
	}
	
	private void updateTableData() {
		BeanItemContainer<Customer> container = new BeanItemContainer<Customer>(Customer.class, dataStore.getPersons());
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
					dataStore.storePerson(((BeanItem<Customer>)fieldGroup.getItemDataSource()).getBean());
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
}
