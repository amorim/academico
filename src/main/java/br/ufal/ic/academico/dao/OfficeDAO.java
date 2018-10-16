package br.ufal.ic.academico.dao;

import br.ufal.ic.academico.model.Office;
import org.hibernate.SessionFactory;

import java.util.List;

public class OfficeDAO extends GenericDAO<Office> {

    public OfficeDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    public List<Office> getAll() {
        return super.getAll(Office.class);
    }
}
