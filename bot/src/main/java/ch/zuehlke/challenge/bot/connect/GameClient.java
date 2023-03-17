package ch.zuehlke.challenge.bot.connect;

import ch.zuehlke.challenge.bot.util.ApplicationProperties;
import ch.zuehlke.common.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static java.lang.System.exit;
import static java.text.MessageFormat.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameClient {

    private final RestTemplate hackathonRestTemplateClient;

    private final ApplicationProperties applicationProperties;


    public PlayerId join() {
        log.info("Joining game...");
        JoinRequest signUpRequest = new JoinRequest(new PlayerName(applicationProperties.getName()));
        // Improve: Handle exceptions
        ResponseEntity<JoinResponse> signUpResponse = hackathonRestTemplateClient
                .postForEntity(applicationProperties.getBackendJoinUrl(),
                        signUpRequest,
                        JoinResponse.class,
                        applicationProperties.getGameId()
                );
        log.info(format("Received response: {0}", signUpResponse));
        if (signUpResponse.getStatusCode().is2xxSuccessful() && signUpResponse.getBody() != null) {
            log.info("Successfully joined game!");
            PlayerId playerId = signUpResponse.getBody().playerId();
            log.info(format("PlayerId: {0}", playerId));
            return playerId;
        } else {
            log.error("Could not join game! Will shutdown now...");
            exit(0);
            // Needed to return something even though exit(0) is called
            return null;
        }
    }

    public void play(Move move) {
        log.info("Playing move {}...", move);

        // Improve: Handle exceptions
        ResponseEntity<Void> response = hackathonRestTemplateClient
                .postForEntity(applicationProperties.getBackendPlayUrl(),
                        move,
                        Void.class,
                        applicationProperties.getGameId()
                );
        log.info(format("Received response: {0}", response));
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Successfully played a move!");
        } else {
            log.error("Could not play game! Will shutdown now...");
            exit(0);
        }
    }
}
