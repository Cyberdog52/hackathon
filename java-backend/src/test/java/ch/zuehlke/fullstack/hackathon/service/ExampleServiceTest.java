package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.fullstack.hackathon.model.ExampleDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExampleServiceTest {

    @Test
    void getExampleDto_successfully() {
        ExampleService exampleService = new ExampleService();

        ExampleDto exampleDto = exampleService.getExampleDto();

        assertThat(exampleDto).isNotNull();
        assertThat(exampleDto.value()).isGreaterThanOrEqualTo(0);
        assertThat(exampleDto.name()).isIn("Example", "Beispiel", "Exemple", "Ejemplar");
    }
}