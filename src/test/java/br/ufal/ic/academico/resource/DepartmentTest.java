package br.ufal.ic.academico.resource;

import br.ufal.ic.academico.model.*;
import br.ufal.ic.academico.util.RestResponse;
import ch.qos.logback.classic.Level;
import com.codahale.metrics.MetricRegistryListener;
import io.dropwizard.logging.BootstrapLogging;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(DropwizardExtensionsSupport.class)
class DepartmentTest extends BaseResourceTest {
    static {
        BootstrapLogging.bootstrap(Level.DEBUG);
    }
    DepartmentTest() {
        fakeApp.setDepartmentDAO(MockDAO.DEPARTMENTDAO_MOCK);
    }
    private AppFaker fakeApp = new AppFaker();
    private final DepartmentResource resource = new DepartmentResource(fakeApp);

    ResourceExtension rule = ResourceExtension.builder()
            .addResource(resource)
            .build();

    @BeforeEach
    @SneakyThrows
    void setUp() {
        super.setUp();
        when(MockDAO.DEPARTMENTDAO_MOCK.getAll()).thenReturn(departments);
    }

    @Test
    void testResourceGetDepartment() {
        log.info("testing get department info from resource");
        Department expected = departments.get(0);
        Department[] saved = rule.target("/department/all").request().get(Department[].class);
        assertThat("department array size", saved.length, greaterThan(0));
        Department d = saved[0];
        assertNotNull(d, "department is null");
        assertEquals(d.getId(), expected.getId(), "ids of departments do not match");
        assertEquals(d.getGraduationOffice().getId(), expected.getGraduationOffice().getId(), "offices of departments do not match");

    }




}
