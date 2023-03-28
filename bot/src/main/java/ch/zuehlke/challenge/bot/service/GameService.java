package ch.zuehlke.challenge.bot.service;

import ch.zuehlke.challenge.bot.brain.Brain;
import ch.zuehlke.challenge.bot.client.GameClient;
import ch.zuehlke.common.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameService {

    private final Brain brain;

    @Getter
    @Setter
    private UUID lobbyId;

    private final GameClient gameClient;

    private final ShutDownService shutDownService;

    // Improve: find a better way to keep track of already processed requests
    private final Set<RequestId> alreadyProcessedRequestIds = new HashSet<>();


    @EventListener(ApplicationReadyEvent.class)
    public void joinGame() {
        this.lobbyId = gameClient.joinLobby();
    }

    public void onGameUpdate(GameUpdate gameUpdate) {
        // Improve: use this to get updates from the bots
        GameDto gameDto = gameUpdate.gameDto();
        if (gameDto.status() == GameStatus.NOT_STARTED) {
            log.info("Not taking any action, game is not started yet...");
            return;
        }
        if (gameDto.status() == GameStatus.FINISHED || gameDto.status() == GameStatus.DELETED) {
            log.info("Game is finished, shutting down...");
            shutDownService.shutDown();
        }

        gameDto.state().currentRequests().stream()
                .filter(request -> !alreadyProcessedRequestIds.contains(request.requestId()))
                .filter(request -> request.playerId().equals(playerId))
                .forEach(this::processRequest);
    }

    private void processRequest(PlayRequest playRequest) {
        log.info("Processing request: {}", playRequest);
        alreadyProcessedRequestIds.add(playRequest.requestId());

        GameAction decision = brain.decide(playRequest.possibleActions());

        Move move = new Move(playerId, playRequest.requestId(), decision);
        gameClient.play(move);
    }


}
