package com.practice.vaadin.persistent.services;

import java.util.List;

import com.practice.vaadin.domain.Customer;

public interface DataStoreService {
	public List<Customer> getPersons();

	public void storePerson(Customer person);

	public void deletePerson(Customer person);
}
