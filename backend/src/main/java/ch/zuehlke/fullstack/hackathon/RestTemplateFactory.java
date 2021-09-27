package ch.zuehlke.fullstack.hackathon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateFactory {
    private final String insightBaseUrl = "https://insight.zuehlke.com";

    private final String insightUser;
    private final String insightPassword;
    private final RestTemplateBuilder restTemplateBuilder;


    public RestTemplateFactory(@Value("${INSIGHT_API_USER:#{null}}") String username,
                               @Value("${INSIGHT_API_PASSWORD:#{null}}") String password,
                               RestTemplateBuilder restTemplateBuilder) {
        this.insightUser = password;
        this.insightPassword = username;
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Bean
    public RestTemplate getInsightRestTemplate() {
        if (insightUser == null || insightPassword == null) {
            throw new IllegalArgumentException("No username or password provided for Insight API");
        } else {
            return restTemplateBuilder
                    .rootUri(insightBaseUrl)
                    .basicAuthentication(insightPassword, insightUser)
                    .build();
        }
    }
}