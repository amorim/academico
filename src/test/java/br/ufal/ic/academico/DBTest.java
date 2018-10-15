package br.ufal.ic.academico;

import br.ufal.ic.academico.exemplos.PersonEx;
import br.ufal.ic.academico.exemplos.PersonExDAO;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import lombok.SneakyThrows;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 *
 * @author Willy
 */
@ExtendWith(DropwizardExtensionsSupport.class)
public class DBTest {
    
    public DAOTestExtension dbTesting = DAOTestExtension.newBuilder()
            .addEntityClass(PersonEx.class).build();
    
    private PersonExDAO dao;

    @BeforeEach
    @SneakyThrows
    public void setUp() {
        System.out.println("setUp");
        dao = new PersonExDAO(dbTesting.getSessionFactory());
    }
    
    @Test
    public void testCRUD() {

        System.out.println("testCRUD");
        
        PersonEx c1 = new PersonEx("c1");
        
        PersonEx saved = dbTesting.inTransaction(() -> dao.persist(c1));
        
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals(c1.getName(), saved.getName());
    }
}
