package services;

import entities.Person;

import java.util.List;

public class PersonService extends Service<Person> {

    public PersonService() {

    }

    @Override
    public List<Person> get() {
        return PersonService.data.getPersons();
    }

    @Override
    public Person getById(long id) {
        return PersonService.data.getPerson(id);
    }

    @Override
    public Person create(Person object) {
        return PersonService.data.createPerson(object);
    }

    @Override
    public boolean remove(long id) {
        return PersonService.data.removePerson(id);
    }

    @Override
    public boolean edit(Person object) {
        return PersonService.data.editPerson(object);
    }
}
