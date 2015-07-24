package com.practice.vaadin.persistent.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.vaadin.domain.Customer;
import com.practice.vaadin.persistent.mongo.repository.CustomerRepository;
import com.practice.vaadin.persistent.services.DataStoreService;

@Service("mongoDataStore")
public class MongoDataStoreServiceImpl implements DataStoreService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public List<Customer> getPersons() {
		return customerRepository.findAll();
	}

	@Override
	public void storePerson(Customer person) {
		customerRepository.save(person);
	}

	@Override
	public void deletePerson(Customer person) {
		customerRepository.delete(person);
	}

}
