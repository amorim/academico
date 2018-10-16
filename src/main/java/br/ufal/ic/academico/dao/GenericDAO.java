package br.ufal.ic.academico.dao;

import br.ufal.ic.academico.model.Subject;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

public abstract class   GenericDAO<T> extends AbstractDAO<T> {

    public GenericDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public T get(Serializable id) throws HibernateException {
        return super.get(id);
    }

    @Override
    public T persist(T entity) throws HibernateException {
        return super.persist(entity);
    }

    public void delete(T entity) throws HibernateException {
        super.currentSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll(Class clazz) {
        return currentSession().createQuery("from " + clazz.getName()).list();
    }
}
