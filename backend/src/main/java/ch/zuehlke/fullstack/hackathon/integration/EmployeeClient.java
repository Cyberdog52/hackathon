package ch.zuehlke.fullstack.hackathon.integration;

import ch.zuehlke.fullstack.hackathon.domain.UserInfo;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class EmployeeClient {
    private final String searchPath = "/api/v1/employees/search";
    private final String baseUrl = "https://insight.zuehlke.com";
    private final RestTemplate restTemplate;
    private final String API_USER = "foo";
    private final String API_PASSWORD = "bar";

    public EmployeeClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .rootUri(baseUrl)
                .basicAuthentication(API_USER, API_PASSWORD)
                .build();
    }

    public List<UserInfo> getEmployees(String searchTerm) {
        String searchResourceUrl = searchPath + "?term=" +
                URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);
        ResponseEntity<List<UserInfo>> response
                = restTemplate.exchange(searchResourceUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        assert (response.getStatusCode()).is2xxSuccessful();
        return response.getBody();
    }
}