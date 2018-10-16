package br.ufal.ic.academico.db;


import br.ufal.ic.academico.dao.CourseDAO;
import br.ufal.ic.academico.dao.PersonDAO;
import br.ufal.ic.academico.model.*;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DropwizardExtensionsSupport.class)
class PersonTest {
    private DAOTestExtension dbTesting = DAOTestExtension.newBuilder()
            .addEntityClass(Subject.class).addEntityClass(Course.class).addEntityClass(Person.class).addEntityClass(Office.class).addEntityClass(Department.class).build();

    private PersonDAO dao;
    private CourseDAO courseDAO;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        dao = new PersonDAO(dbTesting.getSessionFactory());
        courseDAO = new CourseDAO(dbTesting.getSessionFactory());
    }

    @Test
    void testCRUD() {
        Person student = create();
        read(student.getId(), student);
        update(student);
        delete(student);
    }

    private Person create() {
        final Person student = new Person(null, "Gabriel", false, null, 0, null, null);
        final Person saved = dbTesting.inTransaction(() -> dao.persist(student));
        assertNotNull(saved, "person not saved");
        assertNotNull(saved.getId(), "person saved but did not auto generate id");
        assertEquals(student.getId(), saved.getId(), "different ids");
        assertEquals(student.getName(), saved.getName(), "different names");
        assertEquals(student.getCredits(), saved.getCredits(), "different credits");
        return student;
    }

    private void read(Long studentId, Person student) {
        Person recovered = dbTesting.inTransaction(() -> dao.get(studentId));

        assertEquals(student.getId(), recovered.getId(), "different ids");
        assertEquals(student.getName(), recovered.getName(), "different names");
        assertEquals(student.getCredits(), recovered.getCredits(), "different credits");
    }

    private void update(Person student) {
        Course course = new Course();
        course.setName("Teste");
        dbTesting.inTransaction(() -> courseDAO.persist(course));
        student.setPersonCourse(course);

        final Person updated = dbTesting.inTransaction(() -> dao.persist(student));
        assertEquals(student.getId(), updated.getId(), "different ids");
        assertEquals(student.getPersonCourse(), updated.getPersonCourse(), "update was not persisted");
    }

    private void delete(Person student) {
        dbTesting.inTransaction(() -> dao.delete(student));
        assertNull(dbTesting.inTransaction(() -> dao.get(student.getId())), "removal didnt work");
    }
}
