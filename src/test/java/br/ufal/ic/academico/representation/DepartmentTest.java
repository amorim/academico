package br.ufal.ic.academico.representation;

import br.ufal.ic.academico.model.Department;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;

public class DepartmentTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    void serializesToJSON() throws Exception {
        final Department department = new Department(1L, null, null);

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/department.json"), Department.class));

        Assertions.assertThat(MAPPER.writeValueAsString(department)).isEqualTo(expected);
    }
}
