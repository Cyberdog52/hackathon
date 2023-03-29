package ch.zuehlke.challenge.bot.service;

import ch.zuehlke.challenge.bot.brain.Brain;
import ch.zuehlke.challenge.bot.client.MatchClient;
import ch.zuehlke.challenge.bot.client.PlayerClient;
import ch.zuehlke.common.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchService {

    private final Brain brain;
    private final MatchClient matchClient;
    private final PlayerClient playerClient;
    private final ShutDownService shutDownService;
    // Improve: find a better way to keep track of already processed requests
    private final Set<RequestId> alreadyProcessedRequestIds = new HashSet<>();
    @Getter
    @Setter
    private PlayerId playerId;

    @EventListener(ApplicationReadyEvent.class)
    public void joinGame() {
        this.playerId = playerClient.createPlayer(brain.name(), brain.icon());
        final var matches = matchClient.getWaitingOpen();
        final var joinResponse = matchClient.join(playerId.value(), matches.get(0).getId().toString());
    }

    private <T> T pickRandom(final List<T> list) {
        return list.get((int) (Math.random() * list.size()));
    }

    public void onGameUpdate(final GameUpdate gameUpdate) {
        // Improve: use this to get updates from the bots
        final GameDto gameDto = gameUpdate.gameDto();
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

    private void processRequest(final PlayRequest playRequest) {
        log.info("Processing request: {}", playRequest);
        alreadyProcessedRequestIds.add(playRequest.requestId());

        final GameAction decision = brain.decide(playRequest.possibleActions());

        final Move move = new Move(playerId, playRequest.requestId(), decision);
        matchClient.play(move);
    }


}
