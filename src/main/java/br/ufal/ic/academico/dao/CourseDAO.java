package br.ufal.ic.academico.dao;

import br.ufal.ic.academico.model.Course;
import org.hibernate.SessionFactory;

import java.util.List;

public class CourseDAO extends GenericDAO<Course> {
    public CourseDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    public List<Course> getAll() {
        return super.getAll(Course.class);
    }
}
