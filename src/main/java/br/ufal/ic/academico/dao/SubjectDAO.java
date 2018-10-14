package br.ufal.ic.academico.dao;

import br.ufal.ic.academico.model.Subject;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class SubjectDAO extends AbstractDAO<Subject> {

    public SubjectDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
