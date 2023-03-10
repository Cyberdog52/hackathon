package ch.zuehlke.challenge.bot.io;

import ch.zuehlke.challenge.bot.domain.ConnectivitySetup;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static java.text.MessageFormat.format;

public class GameConnector {

    public static void connect(ConnectivitySetup setup) {
        System.out.println(format("Connecting to {0} with name {1} and game value {2}...", setup.uri(), setup.name(), setup.gameId()));
        RestTemplate restTemplate = getRestTemplate(setup);

        SignUpRequest signUpRequest = new SignUpRequest(setup.name().value());
        System.out.println(format("Built signup request: {0}", signUpRequest));
        System.out.println("Sending request...");
        ResponseEntity<SignUpResponse> signUpResponse = restTemplate.postForEntity("/api/lobby/game/{gameId}/join", signUpRequest, SignUpResponse.class, setup.gameId().value());
        System.out.println(format("Received response: {0}", signUpResponse));
    }

    private static RestTemplate getRestTemplate(ConnectivitySetup setup) {
        System.out.println("Building restTemplate...");
        return new RestTemplateBuilder()
                .rootUri(setup.uri().toString())
                .build();
    }
}
