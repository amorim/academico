package br.ufal.ic.academico.dao;

import br.ufal.ic.academico.model.Subject;
import org.hibernate.SessionFactory;

public class SubjectDAO extends GenericDAO<Subject> {

    public SubjectDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}
