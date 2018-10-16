package br.ufal.ic.academico.resource;

import br.ufal.ic.academico.dao.PersonDAO;
import br.ufal.ic.academico.model.*;
import br.ufal.ic.academico.util.RestResponse;
import ch.qos.logback.classic.Level;
import io.dropwizard.logging.BootstrapLogging;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(DropwizardExtensionsSupport.class)
class StudentTest extends BaseResourceTest {
    static {
        BootstrapLogging.bootstrap(Level.DEBUG);
    }
    StudentTest() {
        fakeApp.setPersonDAO(MockDAO.PERSONDAO_MOCK);
        fakeApp.setSubjectDAO(MockDAO.SUBJECTDAO_MOCK);
    }
    private AppFaker fakeApp = new AppFaker();
    private final StudentResource resource = new StudentResource(fakeApp);

    ResourceExtension rule = ResourceExtension.builder()
            .addResource(resource)
            .build();



    @BeforeEach
    @SneakyThrows
    void setUp() {
        super.setUp();
        when(MockDAO.PERSONDAO_MOCK.get(people.get(1).getId())).thenReturn(people.get(1));
        when(MockDAO.SUBJECTDAO_MOCK.get(subjects.get(0).getId())).thenReturn(subjects.get(0));
    }

    @Test
    void testResourceGetStudent() {
        log.info("testing get student info from resource");
        Person saved = rule.target("/student/" + people.get(1).getId()).request().get(Person.class);
        assertNotNull(saved);
        assertEquals(people.get(1).getName(), saved.getName());
        assertEquals(people.get(1).getId(), saved.getId());
    }

    @Test
    void testResourceEnrollStudent() {
        log.info("enrolling student in subject");
        RestResponse r = rule.target("/student/" + people.get(1).getId() + "/enroll/subject/" + subjects.get(0).getId()).request().post(null, RestResponse.class);
        assertNotNull(r);
        assertEquals(r.getCode(), Response.Status.OK.getStatusCode());
        assertEquals(r.getReason(), "Student is now enrolled");
    }




}
