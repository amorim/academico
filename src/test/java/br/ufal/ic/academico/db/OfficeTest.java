package br.ufal.ic.academico.db;

import br.ufal.ic.academico.dao.CourseDAO;
import br.ufal.ic.academico.dao.OfficeDAO;
import br.ufal.ic.academico.model.*;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(DropwizardExtensionsSupport.class)

class OfficeTest {
    private DAOTestExtension dbTesting = DAOTestExtension.newBuilder()
            .addEntityClass(Subject.class).addEntityClass(Course.class).addEntityClass(Person.class).addEntityClass(Office.class).addEntityClass(Department.class).build();

    private OfficeDAO dao;
    private CourseDAO courseDAO;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        dao = new OfficeDAO(dbTesting.getSessionFactory());
        courseDAO = new CourseDAO(dbTesting.getSessionFactory());
    }

    @Test
    void testCRUD() {
        Office office = create();
        read(office.getId(), office);
        update(office);
        delete(office);
    }

    private Office create() {
        Office office = new Office();
        office.setCourses(new ArrayList<>());
        Office saved = dbTesting.inTransaction(() -> dao.persist(office));

        assertNotNull(saved, "office could not be saved");
        assertNotNull(saved.getId(), "persistence failed");
        assertEquals(office.getId(), saved.getId(), "different ids");
        assertEquals(office.getCourses(), saved.getCourses(), "different courses");
        return office;
    }

    private void read(Long officeId, Office office) {
        Office recovered = dbTesting.inTransaction(() -> dao.get(officeId));

        assertEquals(office.getId(), recovered.getId(), "different ids");
        assertEquals(office.getCourses(), recovered.getCourses(), "different courses");
    }

    private void update(Office office) {
        Course course = new Course();
        course.setName("T");
        dbTesting.inTransaction(() -> courseDAO.persist(course));
        office.getCourses().add(course);

        Office updated = dbTesting.inTransaction(() -> dao.persist(office));
        assertEquals(office.getId(), updated.getId(), "different ids");
        assertEquals(office.getCourses(), updated.getCourses(), "different courses");
    }

    private void delete(Office office) {
        dbTesting.inTransaction(() -> dao.delete(office));
        assertNull(dbTesting.inTransaction(() -> dao.get(office.getId())), "error removing office");
    }


}
