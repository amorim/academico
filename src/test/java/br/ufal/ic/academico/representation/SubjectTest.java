package br.ufal.ic.academico.representation;

import br.ufal.ic.academico.model.Person;
import br.ufal.ic.academico.model.Subject;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;

class SubjectTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    void serializesToJSON() throws Exception {
        final Subject s = new Subject(1L, "teste", "Testes", 100L, 0L, false, null, null, null, null);

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/subject.json"), Subject.class));

        Assertions.assertThat(MAPPER.writeValueAsString(s)).isEqualTo(expected);
    }
}
