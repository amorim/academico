package br.ufal.ic.academico.resource;

import br.ufal.ic.academico.model.Subject;
import ch.qos.logback.classic.Level;
import io.dropwizard.logging.BootstrapLogging;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(DropwizardExtensionsSupport.class)
class SubjectResourceTest extends BaseResourceTest {
    static {
        BootstrapLogging.bootstrap(Level.DEBUG);
    }
    SubjectResourceTest() {
        fakeApp.setSubjectDAO(MockDAO.SUBJECTDAO_MOCK);
    }
    private AppFaker fakeApp = new AppFaker();
    private final SubjectResource resource = new SubjectResource(fakeApp);

    ResourceExtension rule = ResourceExtension.builder()
            .addResource(resource)
            .build();

    @BeforeEach
    @SneakyThrows
    void setUp() {
        super.setUp();
        when(MockDAO.SUBJECTDAO_MOCK.get(subjects.get(0).getId())).thenReturn(subjects.get(0));
    }

    @Test
    void testResourceGetSubject() {
        log.info("testing get subject info from resource");
        Subject expected = subjects.get(0);
        Subject saved = rule.target("/subject/" + expected.getId()).request().get(Subject.class);
        assertNotNull(saved, "subject is null");
        assertEquals(expected.getId(), saved.getId(), "subjects IDs are different");
        assertEquals(expected.getName(), saved.getName(), "subjects names are different");
        assertEquals(expected.getCode(), saved.getCode(), "subjects codes are different");
        assertEquals(expected.getAssociatedCredits(), saved.getAssociatedCredits(), "subjects associated credits are different");
        assertEquals(expected.getCreditsNeeded(), saved.getCreditsNeeded(), "subjects credits requirements are different");
    }
}
