package br.ufal.ic.academico.exemplos;

import io.dropwizard.hibernate.AbstractDAO;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import java.io.Serializable;

/**
 *
 * @author Willy
 */
@Slf4j
public class PersonExDAO extends AbstractDAO<PersonEx> {
    
    public PersonExDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public PersonEx get(Serializable id) throws HibernateException {
        log.info("getting person: id={}", id);
        return super.get(id);
    }
    
    @Override
    public PersonEx persist(PersonEx entity) throws HibernateException {
        return super.persist(entity);
    }
}
