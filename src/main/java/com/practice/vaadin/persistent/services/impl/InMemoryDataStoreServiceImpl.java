package com.practice.vaadin.persistent.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.practice.vaadin.domain.Customer;
import com.practice.vaadin.persistent.services.DataStoreService;

@Service("inMemoryDataStore")
public class InMemoryDataStoreServiceImpl implements DataStoreService {
	private List<Customer> personList = new ArrayList<Customer>();
	

	@Override
	public List<Customer> getPersons() {
		return personList;
	}

	@Override
	public void storePerson(Customer person) {
		personList.add(person);
	}

	@Override
	public void deletePerson(Customer person) {
		personList.remove(person);
	}

}
