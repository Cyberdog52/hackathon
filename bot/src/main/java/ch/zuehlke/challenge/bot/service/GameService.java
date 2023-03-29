package ch.zuehlke.challenge.bot.service;

import ch.zuehlke.challenge.bot.brain.Brain;
import ch.zuehlke.challenge.bot.client.GameClient;
import ch.zuehlke.challenge.bot.util.ApplicationProperties;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class GameService {

    private final Brain brain;

    @Getter
    @Setter
    private PlayerId playerId;

    private final GameClient gameClient;

    // Improve: find a better way to keep track of already processed requests
    private final Set<RequestId> alreadyProcessedRequestIds = new HashSet<>();

    private final ApplicationProperties applicationProperties;

    @EventListener(ApplicationReadyEvent.class)
    public void joinTournamentOrGame() {
        if (applicationProperties.isTournamentBot()) {
            this.playerId = gameClient.joinTournament();
        } else {
            this.playerId = gameClient.join();
        }
    }

    public void onGameUpdate(GameUpdate gameUpdate) {
        // Improve: use this to get updates from the bots
        GameDto gameDto = gameUpdate.gameDto();
        if (gameDto.status() == GameStatus.NOT_STARTED) {
            log.info("Not taking any action, game is not started yet...");
            return;
        }

        gameDto.state().currentRequests().stream()
                .filter(request -> !alreadyProcessedRequestIds.contains(request.requestId()))
                .filter(request -> request.playerId().equals(playerId))
                .forEach(this::processRequest);
    }

    private void processRequest(PlayRequest playRequest) {
        log.info("Processing request: {}", playRequest);
        alreadyProcessedRequestIds.add(playRequest.requestId());

        GameAction decision = brain.decide(playRequest.attacker(), playRequest.board(), playRequest.possibleActions());

        Move move = new Move(playerId, playRequest.requestId(), playRequest.gameId(), decision);
        gameClient.play(move);
    }


}
