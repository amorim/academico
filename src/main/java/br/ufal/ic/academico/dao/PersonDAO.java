package br.ufal.ic.academico.dao;

import br.ufal.ic.academico.model.Person;
import org.hibernate.SessionFactory;

import java.util.List;

public class PersonDAO extends GenericDAO<Person> {
    public PersonDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Person> getAll() {
        return super.getAll(Person.class);
    }
}
