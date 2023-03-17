package ch.zuehlke.challenge.bot.client;

import ch.zuehlke.challenge.bot.util.ApplicationProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RestTemplateConfigurationTest {

    private ApplicationProperties applicationPropertiesMock;
    private RestTemplateConfiguration restTemplateConfiguration;

    @BeforeEach
    void setUp() {
        applicationPropertiesMock = mock(ApplicationProperties.class);
        restTemplateConfiguration = new RestTemplateConfiguration(applicationPropertiesMock);
    }

    @Test
    void restTemplate_withValidInput_isBuiltSuccessfully() {
        when(applicationPropertiesMock.getBackendRootUri()).thenReturn("http://localhost:8080");

        RestTemplate restTemplate = restTemplateConfiguration.restTemplate();

        assertThat(restTemplate).isNotNull();
    }
}