package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.fullstack.hackathon.model.UserInfo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class InsightClient {
    private final String searchPath = "/api/v1/employees/search";
    private final RestTemplate restTemplate;
    
    public InsightClient(RestTemplateFactory factory) {
        this.restTemplate = factory.getInsightRestTemplate();
    }

    public List<UserInfo> getEmployees(String searchTerm) {
        String uriPath = UriComponentsBuilder.fromPath(searchPath).queryParam("term", 
                URLEncoder.encode(searchTerm, StandardCharsets.UTF_8))
                .toUriString();
        ResponseEntity<List<UserInfo>> response
                = restTemplate.exchange(uriPath, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        assert (response.getStatusCode()).is2xxSuccessful();
        return response.getBody();
    }
}