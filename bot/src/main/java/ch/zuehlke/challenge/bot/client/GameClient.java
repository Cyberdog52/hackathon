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

    public Player register() {
        var registerRequest = new RegisterRequest(applicationProperties.getName());
        log.info("Joining game with request {}", registerRequest);
        log.info(applicationProperties.getBackendRegisterUrl());

        // Improve: Handle exceptions
        var response = hackathonRestTemplateClient
                .postForEntity(applicationProperties.getBackendRegisterUrl(),
                        registerRequest,
                        RegisterResponse.class
                );
        log.info("Received response: {}", response);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            var player = response.getBody().getPlayer();
            log.info("Joined game with Player: {}", player);
            return player;
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
