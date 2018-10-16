package br.ufal.ic.academico.integration;

import br.ufal.ic.academico.AcademicoApp;
import br.ufal.ic.academico.ConfigApp;
import br.ufal.ic.academico.model.Subject;
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

@Slf4j
@ExtendWith(DropwizardExtensionsSupport.class)
public class SubjectTest {
    static {
        BootstrapLogging.bootstrap(Level.DEBUG);
    }

    SubjectTest() {
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
    void testRetrieveSubject() {
        Response r = rule.client().target(String.format(
                "http://localhost:%d/%s/subject/%d",
                rule.getLocalPort(),
                "academicotest",
                0)).request().get();
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), r.getStatus(), "Given ID is invalid");

        Subject s = rule.client().target(String.format(
                "http://localhost:%d/%s/subject/%d",
                rule.getLocalPort(),
                "academicotest",
                6)).request().get(Subject.class);

        assertEquals("COMP201", s.getCode(), "Code is different");
        assertEquals(6L, (long)s.getId(), "IDs are different");
    }
}
