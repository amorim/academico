package br.ufal.ic.academico.db;

import br.ufal.ic.academico.dao.CourseDAO;
import br.ufal.ic.academico.dao.SubjectDAO;
import br.ufal.ic.academico.model.*;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DropwizardExtensionsSupport.class)
class CourseTest {
    private DAOTestExtension dbTesting = DAOTestExtension.newBuilder()
            .addEntityClass(Subject.class).addEntityClass(Course.class).addEntityClass(Person.class).addEntityClass(Office.class).addEntityClass(Department.class).build();

    private CourseDAO dao;
    private SubjectDAO subjectDAO;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        dao = new CourseDAO(dbTesting.getSessionFactory());
        subjectDAO = new SubjectDAO(dbTesting.getSessionFactory());
    }

    @Test
    void testCRUD() {
        Course course = create();
        read(course.getId(), course);
        update(course);
        delete(course);
    }

    private Course create() {
        Course course = new Course();
        course.setName("CCOMP");
        course.setSubjects(new ArrayList<>());
        Course saved = dbTesting.inTransaction(() -> dao.persist(course));

        assertNotNull(saved, "course not saved");
        assertNotNull(saved.getId(), "persistence didnt create an id");
        assertEquals(course.getId(), saved.getId(), "different ids");
        assertNotNull(saved.getSubjects(), "null subjects list");
        assertEquals(0, saved.getSubjects().size(), "course has subjects, but it was just created");
        return course;
    }

    private void read(Long courseId, Course course) {
        Course recovered = dbTesting.inTransaction(() -> dao.get(courseId));

        assertEquals(course.getId(), recovered.getId(), "different ids");
        assertEquals(course.getSubjects(), recovered.getSubjects(), "course subjects do not match");
    }

    private void update(Course course) {
        Subject subject = new Subject(null, "teste", "Testes", 100L, 0L, false, null, null, null, null);
        dbTesting.inTransaction(() -> subjectDAO.persist(subject));
        course.getSubjects().add(subject);
        Course updated = dbTesting.inTransaction(() -> dao.persist(course));
        assertEquals(course.getId(), updated.getId(), "different ids");
        assertEquals(course.getSubjects(), updated.getSubjects(), "different subjects");
    }

    private void delete(Course course) {
        dbTesting.inTransaction(() -> dao.delete(course));
        assertNull(dbTesting.inTransaction(() -> dao.get(course.getId())), "error removing course");
    }

}
