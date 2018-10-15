package br.ufal.ic.academico.dao;

import br.ufal.ic.academico.model.Department;
import org.hibernate.SessionFactory;

import java.util.List;

public class DepartmentDAO extends GenericDAO<Department> {

    public DepartmentDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Department> getAll() {
        return null;
    }
}
