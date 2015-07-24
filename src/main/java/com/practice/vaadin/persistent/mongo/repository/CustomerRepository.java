package com.practice.vaadin.persistent.mongo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.practice.vaadin.domain.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

	List<Customer> findByFirstName(String firstname);
	
	List<Customer> findByLastName(String lastname);
}
