package br.ufal.ic.academico.dao;

import br.ufal.ic.academico.model.Course;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class CourseDAO extends AbstractDAO<Course> {
    public CourseDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
