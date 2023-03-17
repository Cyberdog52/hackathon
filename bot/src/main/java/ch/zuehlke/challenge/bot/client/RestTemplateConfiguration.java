package ch.zuehlke.challenge.bot.client;

import ch.zuehlke.challenge.bot.util.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Configuration
public class RestTemplateConfiguration {

    private final ApplicationProperties applicationProperties;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .rootUri(applicationProperties.getBackendRootUri())
                .build();
    }

}
