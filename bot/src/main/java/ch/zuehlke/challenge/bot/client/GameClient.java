package ch.zuehlke.challenge.bot.client;

import ch.zuehlke.challenge.bot.service.ShutDownService;
import ch.zuehlke.challenge.bot.util.ApplicationProperties;
import ch.zuehlke.common.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameClient {

    private final RestTemplate hackathonRestTemplateClient;

    private final ApplicationProperties applicationProperties;

    private final ShutDownService shutDownService;

    public PlayerId register() {
        var registerRequest = new RegisterRequest(applicationProperties.getName());
        log.info("Joining game with request {}", registerRequest);
        log.info(applicationProperties.getBackendRegisterUrl());

        // Improve: Handle exceptions
        ResponseEntity<RegisterResponse> signUpResponse = hackathonRestTemplateClient
                .postForEntity(applicationProperties.getBackendRegisterUrl(),
                        registerRequest,
                        RegisterResponse.class
                );
        log.info("Received response: {}", signUpResponse);
        if (signUpResponse.getStatusCode().is2xxSuccessful() && signUpResponse.getBody() != null) {
            PlayerId playerId = new PlayerId(signUpResponse.getBody().uuid());
            log.info("Joined game with PlayerId: {}", playerId);
            return playerId;
        } else {
            log.error("Could not join game. Will shutdown now...");
            shutDownService.shutDown();
            // Needed to return something even though exit(0) is called
            return null;
        }
    }

    public void play(Move move) {
        log.info("Playing move: {}", move);

        // Improve: Handle exceptions
        ResponseEntity<Void> response = hackathonRestTemplateClient
                .postForEntity(applicationProperties.getBackendPlayUrl(),
                        move,
                        Void.class,
                        applicationProperties.getGameId()
                );
        log.info("Received response: {}", response);
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Successfully played a move!");
        } else {
            log.error("Could not play game! Will shutdown now...");
            shutDownService.shutDown();
        }
    }
}
