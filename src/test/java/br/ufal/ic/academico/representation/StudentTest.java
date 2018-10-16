package br.ufal.ic.academico.representation;

import br.ufal.ic.academico.model.Course;
import br.ufal.ic.academico.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;

class StudentTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    void serializesToJSON() throws Exception {
        final Person person = new Person(1L, "Lucas", false, null, 0, null, null);

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/person.json"), Person.class));

        Assertions.assertThat(MAPPER.writeValueAsString(person)).isEqualTo(expected);
    }
}
