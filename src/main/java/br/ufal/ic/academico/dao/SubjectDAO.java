package br.ufal.ic.academico.dao;

import br.ufal.ic.academico.model.Subject;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class SubjectDAO extends GenericDAO<Subject> {

    public SubjectDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Subject> getAll() {
        return (List<Subject>) currentSession().createQuery("from Subject").list();
    }
}
