package br.ufal.ic.academico.db;

import br.ufal.ic.academico.dao.DepartmentDAO;
import br.ufal.ic.academico.dao.OfficeDAO;
import br.ufal.ic.academico.model.*;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DropwizardExtensionsSupport.class)
class DepartmentTest {
    private DAOTestExtension dbTesting = DAOTestExtension.newBuilder()
            .addEntityClass(Subject.class).addEntityClass(Course.class).addEntityClass(Person.class).addEntityClass(Office.class).addEntityClass(Department.class).build();

    private DepartmentDAO dao;
    private OfficeDAO officeDAO;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        dao = new DepartmentDAO(dbTesting.getSessionFactory());
        officeDAO = new OfficeDAO(dbTesting.getSessionFactory());
    }

    @Test
    void testCRUD() {
        Department department = create();
        read(department.getId(), department);
        update(department);
        delete(department);
    }

    private Department create() {
        final Department department = new Department();
        final Department saved = dbTesting.inTransaction(() -> dao.persist(department));

        assertNotNull(saved, "department not saved");
        assertNotNull(saved.getId(), "no id during persistence");
        assertEquals(department.getId(), saved.getId(), "different ids");
        assertNull(saved.getPostGraduationOffice(), "non null post graduation office");
        assertNull(saved.getGraduationOffice(), "non null undergrad office");

        return department;
    }

    private void read(Long departmentId, Department department) {
        Department recovered = dbTesting.inTransaction(() -> dao.get(departmentId));

        assertEquals(department.getId(), recovered.getId(), "different IDs");
        assertEquals(department.getPostGraduationOffice(), recovered.getPostGraduationOffice(), "different post graduation offices");
        assertEquals(department.getGraduationOffice(), recovered.getGraduationOffice(), "different undergraduation offices");
    }

    private void update(Department department) {
        final Office postDegreeOffice = new Office();
        dbTesting.inTransaction(() -> officeDAO.persist(postDegreeOffice));
        department.setPostGraduationOffice(postDegreeOffice);
        final Office degreeOffice = new Office();
        dbTesting.inTransaction(() -> officeDAO.persist(degreeOffice));
        department.setGraduationOffice(degreeOffice);

        final Department updated = dbTesting.inTransaction(() -> dao.persist(department));
        assertEquals(department.getId(), updated.getId(), "id changed during persistence update");
        assertEquals(department.getPostGraduationOffice(), updated.getPostGraduationOffice(), "during update, post graduation office changed");
        assertEquals(department.getGraduationOffice(), updated.getGraduationOffice(), "during update, undergraduation office changed");
    }

    private void delete(Department department) {
        dbTesting.inTransaction(() -> dao.delete(department));
        assertNull(dbTesting.inTransaction(() -> dao.get(department.getId())), "problem removing department");
    }
}
