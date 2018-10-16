package br.ufal.ic.academico.db;

import br.ufal.ic.academico.dao.SubjectDAO;
import br.ufal.ic.academico.model.*;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(DropwizardExtensionsSupport.class)
class SubjectTest {
    private DAOTestExtension dbTesting = DAOTestExtension.newBuilder()
            .addEntityClass(Subject.class).addEntityClass(Course.class).addEntityClass(Person.class).addEntityClass(Office.class).addEntityClass(Department.class).build();

    private SubjectDAO dao;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        dao = new SubjectDAO(dbTesting.getSessionFactory());
    }

    @Test
    void testCRUD() {
        Subject subject = create();
        read(subject.getId(), subject);
        update(subject);
        delete(subject);
    }

    private Subject create() {
        final Subject subject = new Subject(null, "teste", "Testes", 100L, 0L, false, null, null, null, null);
        final Subject saved = dbTesting.inTransaction(() -> dao.persist(subject));

        assertNotNull(saved, "error during subject saving");
        assertNotNull(saved.getId(), "no id during persistence");
        assertEquals(subject.getId(), saved.getId(), "different ids");
        assertEquals(subject.getName(), saved.getName(), "different names");
        assertEquals(subject.getAssociatedCredits(), saved.getAssociatedCredits(), "different associated credits");
        assertEquals(subject.getCode(), saved.getCode(), "different codes");
        assertEquals(subject.getCreditsNeeded(), saved.getCreditsNeeded(), "different required credits");


        return subject;
    }

    private void read(Long subjectId, Subject subject) {
        Subject recovered = dbTesting.inTransaction(() -> dao.get(subjectId));

        assertEquals(subject.getId(), recovered.getId(), "different ids");
        assertEquals(subject.getName(), recovered.getName(), "different names");
        assertEquals(subject.getAssociatedCredits(), recovered.getAssociatedCredits(), "different associated credits");
        assertEquals(subject.getCode(), recovered.getCode(), "different codes");
        assertEquals(subject.getCreditsNeeded(), recovered.getCreditsNeeded(), "different required credits");
    }

    private void update(Subject subject) {
        subject.setName("Teste de Software");

        final Subject updated = dbTesting.inTransaction(() -> dao.persist(subject));
        assertEquals(subject.getId(), updated.getId(), "different ids");
        assertEquals(subject.getName(), updated.getName(), "different names");
        assertEquals(subject.getAssociatedCredits(), updated.getAssociatedCredits(), "different associated credits");
        assertEquals(subject.getCode(), updated.getCode(), "different codes");
        assertEquals(subject.getCreditsNeeded(), updated.getCreditsNeeded(), "different required credits");
    }

    private void delete(Subject subject) {
        dbTesting.inTransaction(() -> dao.delete(subject));
        assertNull(dbTesting.inTransaction(() -> dao.get(subject.getId())), "error removing subject");
    }

}
