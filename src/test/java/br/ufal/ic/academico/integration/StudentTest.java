package br.ufal.ic.academico.integration;

import br.ufal.ic.academico.AcademicoApp;
import br.ufal.ic.academico.ConfigApp;
import br.ufal.ic.academico.model.Person;
import br.ufal.ic.academico.resource.AppFaker;
import br.ufal.ic.academico.resource.MockDAO;
import br.ufal.ic.academico.resource.StudentResource;
import ch.qos.logback.classic.Level;
import io.dropwizard.logging.BootstrapLogging;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@ExtendWith(DropwizardExtensionsSupport.class)
class StudentTest {
    static {
        BootstrapLogging.bootstrap(Level.DEBUG);
    }

    StudentTest() {
        app.setPersonDAO(MockDAO.PERSONDAO_MOCK);
        app.setSubjectDAO(MockDAO.SUBJECTDAO_MOCK);
    }

    private AppFaker app = new AppFaker();

    private final StudentResource resource = new StudentResource(app);

    @SuppressWarnings("unchecked")
    public static DropwizardAppExtension<ConfigApp> rule = new DropwizardAppExtension(AcademicoApp.class,
            ResourceHelpers.resourceFilePath("config-test.yml"));

    @BeforeAll
    @SneakyThrows
    static void setUp() {
        rule.client().target(String.format(
                "http://localhost:%d/%s/util/loadData",
                rule.getLocalPort(),
                "academicotest")
        ).request().get();
    }

    @Test
    void retrieveStudent() {
        Response r = rule.client().target(
                String.format(
                        "http://localhost:%d/%s/student/%d",
                        rule.getLocalPort(),
                        "academicotest",
                        0
                )
        ).request().get();
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), r.getStatus(), "invalid id should return a not found error");
        r = rule.client().target(
                String.format(
                        "http://localhost:%d/%s/student/%d",
                        rule.getLocalPort(),
                        "academicotest",
                        5
                )
        ).request().get();
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), r.getStatus(), "teacher id on student query should return a bad request error");

        Person s = rule.client().target(
                String.format(
                        "http://localhost:%d/%s/student/%d",
                        rule.getLocalPort(),
                        "academicotest",
                        12
                )
        ).request().get(Person.class);
        assertNotNull(s, "Person is null");
        assertEquals("Lucas Amorim", s.getName(), "Names are different");
        assertEquals(12L, (long)s.getId(), "IDs are different");
    }

    @Test
    void enrollStudent() {
        Response r = rule.client().target(
                String.format(
                        "http://localhost:%d/%s/student/%d/enroll/subject/%d",
                        rule.getLocalPort(),
                        "academicotest",
                        0,
                        4
                )
        ).request().post(null);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), r.getStatus(), "invalid student id should return a not found error");

        r = rule.client().target(
                String.format(
                        "http://localhost:%d/%s/student/%d/enroll/subject/%d",
                        rule.getLocalPort(),
                        "academicotest",
                        5,
                        4
                )
        ).request().post(null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), r.getStatus(), "id of a teacher during enrollment should return a bad request error");

        r = rule.client().target(
                String.format(
                        "http://localhost:%d/%s/student/%d/enroll/subject/%d",
                        rule.getLocalPort(),
                        "academicotest",
                        12,
                        0
                )
        ).request().post(null);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), r.getStatus(), "invalid subject id should return a not found error");

        r = rule.client().target(
                String.format(
                        "http://localhost:%d/%s/student/%d/enroll/subject/%d",
                        rule.getLocalPort(),
                        "academicotest",
                        12,
                        8
                )
        ).request().post(null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), r.getStatus(), "student has 0 credits, not enough to enroll");

        r = rule.client().target(
                String.format(
                        "http://localhost:%d/%s/student/%d/enroll/subject/%d",
                        rule.getLocalPort(),
                        "academicotest",
                        12,
                        10
                )
        ).request().post(null);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), r.getStatus(), "not all requirements done to enroll into this subject");


        r = rule.client().target(
                String.format(
                        "http://localhost:%d/%s/student/%d/enroll/subject/%d",
                        rule.getLocalPort(),
                        "academicotest",
                        12,
                        6
                )
        ).request().post(null);
        assertEquals(Response.Status.OK.getStatusCode(), r.getStatus(), "student meets all requirements for enrollment");
        // NOW student 12 has Computer Science associated
        r = rule.client().target(
                String.format(
                        "http://localhost:%d/%s/student/%d/enroll/subject/%d",
                        rule.getLocalPort(),
                        "academicotest",
                        12,
                        16
                )
        ).request().post(null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), r.getStatus(), "this subject is not part of the students course");

        r = rule.client().target(
                String.format(
                        "http://localhost:%d/%s/student/%d/enroll/subject/%d",
                        rule.getLocalPort(),
                        "academicotest",
                        12,
                        6
                )
        ).request().post(null);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), r.getStatus(), "student already enrolled to this subject");

        r = rule.client().target(
                String.format(
                        "http://localhost:%d/%s/student/%d/enroll/subject/%d",
                        rule.getLocalPort(),
                        "academicotest",
                        12,
                        7
                )
        ).request().post(null);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), r.getStatus(), "student doesnt have the minimum of 170 credits for enrollment in postdegree");

        r = rule.client().target(
                String.format(
                        "http://localhost:%d/%s/student/%d/enroll/subject/%d",
                        rule.getLocalPort(),
                        "academicotest",
                        18,
                        7
                )
        ).request().post(null);

        assertEquals(Response.Status.OK.getStatusCode(), r.getStatus(), "student meets all requirements for enrollment");

        r = rule.client().target(
                String.format(
                        "http://localhost:%d/%s/student/%d/enroll/subject/%d",
                        rule.getLocalPort(),
                        "academicotest",
                        18,
                        6
                )
        ).request().post(null);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), r.getStatus(), "post-degree student cant enroll in undergraduate subject");

    }

}
