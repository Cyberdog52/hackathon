package ch.zuehlke.challenge.bot.service;

import ch.zuehlke.challenge.bot.brain.Brain;
import ch.zuehlke.challenge.bot.client.GameClient;
import ch.zuehlke.challenge.bot.util.ApplicationProperties;
import ch.zuehlke.common.*;
import ch.zuehlke.common.gameplay.PlaceShipsRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameService {

    private final Brain brain;

    private final RestTemplate hackathonRestTemplateClient;

    @Getter
    @Setter
    private Player player;

    private final GameClient gameClient;

    private final ShutDownService shutDownService;
    private final ApplicationProperties applicationProperties;

    // Improve: find a better way to keep track of already processed requests
    private final Set<RequestId> alreadyProcessedRequestIds = new HashSet<>();


    @EventListener(ApplicationReadyEvent.class)
    public void registerPlayer() {
        this.player = gameClient.register();
    }

    public void onGameUpdate(GameUpdate gameUpdate) {
        // Improve: use this to get updates from the bots
        GameDto gameDto = gameUpdate.gameDto();
        if (gameDto.status() == GameStatus.CREATED) {
            log.info("Not taking any action, game is not started yet...");

            return;
        }
        if (gameDto.status() == GameStatus.FINISHED || gameDto.status() == GameStatus.DELETED) {
            log.info("Game is finished, shutting down...");
            shutDownService.shutDown();
        }

        if (gameDto.status() == GameStatus.PLACE_SHIPS) {
            PlaceShipsRequest request = brain.createGame(gameUpdate.gameDto().id(), player);
            var response = hackathonRestTemplateClient
                    .postForEntity(applicationProperties.getBackendRegisterUrl(),
                            request,
                            Void.class
                    );
            log.info(response.getBody().toString());
            return;
        }

        gameDto.state().currentRequests().stream()
                .filter(request -> !alreadyProcessedRequestIds.contains(request.requestId()))
                .filter(request -> request.playerId().equals(player))
                .forEach(this::processShoot);
    }

    private void processBoardCreation(PlayRequest playRequest) {
        /*log.info("Processing request: {}", playRequest);
        alreadyProcessedRequestIds.add(playRequest.requestId());

        GameAction decision = brain.(playRequest.possibleActions());

        Move move = new Move(playerId, playRequest.requestId(), decision);
        gameClient.play(move);*/
    }

    private void processShoot(PlayRequest playRequest) {
        log.info("Processing request: {}", playRequest);
        alreadyProcessedRequestIds.add(playRequest.requestId());

        // GameAction decision = brain.decide(playRequest.possibleActions());

        // Move move = new Move(playerId, playRequest.requestId(), decision);
        // gameClient.play(move);
    }


}
