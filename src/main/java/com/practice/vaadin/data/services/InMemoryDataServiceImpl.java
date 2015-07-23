package com.practice.vaadin.data.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.practice.vaadin.ui.main.Person;

@Service
public class InMemoryDataServiceImpl implements IBackend {
	private List<Person> personList = new ArrayList<Person>();
	

	@Override
	public List<Person> getPersons() {
		return personList;
	}

	@Override
	public void storePerson(Person person) {
		personList.add(person);
	}

	@Override
	public void deletePerson(Person person) {
		personList.remove(person);
	}

}
