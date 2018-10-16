package br.ufal.ic.academico.representation;

import br.ufal.ic.academico.model.Office;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;

public class OfficeTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    void serializesToJSON() throws Exception {
        final Office office = new Office(1L, null, null, false);

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/office.json"), Office.class));

        Assertions.assertThat(MAPPER.writeValueAsString(office)).isEqualTo(expected);
    }
}
