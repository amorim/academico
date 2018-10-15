package br.ufal.ic.academico.dao;

import br.ufal.ic.academico.model.Course;
import org.hibernate.SessionFactory;

import java.util.List;

public class CourseDAO extends GenericDAO<Course> {
    public CourseDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Course> getAll() {
        return null;
    }
}
