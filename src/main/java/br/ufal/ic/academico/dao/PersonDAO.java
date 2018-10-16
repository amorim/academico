package br.ufal.ic.academico.dao;

import br.ufal.ic.academico.model.Person;
import org.hibernate.SessionFactory;

public class PersonDAO extends GenericDAO<Person> {
    public PersonDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
