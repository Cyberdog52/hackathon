package ch.zuehlke.challenge.bot.client;

import ch.zuehlke.challenge.bot.service.ShutDownService;
import ch.zuehlke.challenge.bot.util.ApplicationProperties;
import ch.zuehlke.common.Move;
import ch.zuehlke.common.shared.action.lobby.PlayerJoinAction;
import ch.zuehlke.common.shared.event.lobby.PlayerJoinEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameClient {

    @NonNull
    private final RestTemplate hackathonRestTemplateClient;

    @NonNull
    private final ApplicationProperties applicationProperties;

    @NonNull
    private final ShutDownService shutDownService;

    public UUID joinLobby() {
        PlayerJoinAction playerJoinAction = PlayerJoinAction.builder()
                .gameId(applicationProperties.getGameId())
                .playerId(applicationProperties.getPlayerId())
                .build();

        ResponseEntity<PlayerJoinEvent> response = hackathonRestTemplateClient
                .postForEntity(applicationProperties.getBackendJoinUrl(),
                        playerJoinAction,
                        PlayerJoinEvent.class);

        PlayerJoinEvent playerJoinEvent = handleResponseError(response);
        return playerJoinEvent.gameId();
    }


    private <T> T handleResponseError(final ResponseEntity<T> response) {
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
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
