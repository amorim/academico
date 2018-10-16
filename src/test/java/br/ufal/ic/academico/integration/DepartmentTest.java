package br.ufal.ic.academico.integration;

import br.ufal.ic.academico.AcademicoApp;
import br.ufal.ic.academico.ConfigApp;
import br.ufal.ic.academico.model.Department;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@ExtendWith(DropwizardExtensionsSupport.class)
public class DepartmentTest {
    static {
        BootstrapLogging.bootstrap(Level.DEBUG);
    }

    DepartmentTest() {
        app.setDepartmentDAO(MockDAO.DEPARTMENTDAO_MOCK);
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
    void retrieveAllDepartments() {
        Department[] departments = rule.client().target(String.format(
                "http://localhost:%d/%s/department/all",
                rule.getLocalPort(),
                "academicotest")
        ).request().get(Department[].class);

        assertThat("departments array size", 2, equalTo(departments.length));
        assertNotNull(departments[0].getId(), "first department id is null");
        assertNotNull(departments[1].getId(), "second department id is null");
    }

}
