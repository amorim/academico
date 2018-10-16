package br.ufal.ic.academico.representation;

import br.ufal.ic.academico.model.Course;
import br.ufal.ic.academico.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;

public class CourseTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    void serializesToJSON() throws Exception {
        final Course course = new Course(1L, "Teste", null, null);

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/course.json"), Course.class));

        Assertions.assertThat(MAPPER.writeValueAsString(course)).isEqualTo(expected);
    }
}
