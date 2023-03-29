package ch.zuehlke.challenge.bot.client;

import ch.zuehlke.challenge.bot.service.ShutDownService;
import ch.zuehlke.challenge.bot.util.ApplicationProperties;
import ch.zuehlke.common.Coordinate;
import ch.zuehlke.common.Move;
import ch.zuehlke.common.shared.action.lobby.PlayerJoinAction;
import ch.zuehlke.common.shared.action.playing.AttackTurnAction;
import ch.zuehlke.common.shared.action.setup.BoatDirection;
import ch.zuehlke.common.shared.action.setup.BoatType;
import ch.zuehlke.common.shared.action.setup.PlaceBoatAction;
import ch.zuehlke.common.shared.event.lobby.PlayerJoinEvent;
import ch.zuehlke.common.shared.event.playing.AttackEvent;
import ch.zuehlke.common.shared.event.setup.PlaceBoatEvent;
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

    public boolean placeCoordinate(final Coordinate coordinate, final BoatType boatType,
                                   final BoatDirection boatDirection) {
        PlaceBoatAction placeBoatAction = PlaceBoatAction.builder()
                .gameId(applicationProperties.getGameId())
                .playerId(applicationProperties.getPlayerId())
                .coordinate(coordinate)
                .boatDirection(boatDirection)
                .boatType(boatType)
                .build();

        ResponseEntity<PlaceBoatEvent> response = hackathonRestTemplateClient
                .postForEntity(applicationProperties.getBackendSetupUrl(),
                        placeBoatAction,
                        PlaceBoatEvent.class);

        PlaceBoatEvent placeBoatEvent = handleResponseError(response);
        return placeBoatEvent.successful();
    }

    public AttackEvent attack(final Coordinate coordinate) {
        AttackTurnAction attackTurnAction = AttackTurnAction.builder()
                .gameId(applicationProperties.getGameId())
                .playerId(applicationProperties.getPlayerId())
                .coordinate(coordinate)
                .build();

        ResponseEntity<AttackEvent> response = hackathonRestTemplateClient
                .postForEntity(applicationProperties.getBackendPlayUrl(),
                        attackTurnAction,
                        AttackEvent.class);

        return handleResponseError(response);
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
