package com.practice.vaadin.data.services;

import java.util.List;

import com.practice.vaadin.ui.main.Person;

public interface IBackend {
	public List<Person> getPersons();

	public void storePerson(Person person);

	public void deletePerson(Person person);
}
