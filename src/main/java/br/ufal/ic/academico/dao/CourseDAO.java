package br.ufal.ic.academico.dao;

import br.ufal.ic.academico.model.Course;
import org.hibernate.SessionFactory;

public class CourseDAO extends GenericDAO<Course> {
    public CourseDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
