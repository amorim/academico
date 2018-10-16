package br.ufal.ic.academico.dao;

import br.ufal.ic.academico.model.Department;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class DepartmentDAO extends GenericDAO<Department> {

    public DepartmentDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    public List<Department> getAll() {
        return super.getAll(Department.class);
    }
}
