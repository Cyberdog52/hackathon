package ch.zuehlke.challenge.bot.service;

import ch.zuehlke.challenge.bot.brain.Brain;
import ch.zuehlke.challenge.bot.client.GameClient;
import ch.zuehlke.challenge.bot.model.BoatInformation;
import ch.zuehlke.challenge.bot.util.ApplicationProperties;
import ch.zuehlke.common.GameUpdate;
import ch.zuehlke.common.PlayRequest;
import ch.zuehlke.common.RequestId;
import ch.zuehlke.common.shared.action.playing.AttackTurnAction;
import ch.zuehlke.common.shared.event.EventType;
import ch.zuehlke.common.shared.event.playing.TakeTurnEvent;
import ch.zuehlke.common.shared.event.setup.GameConfigEvent;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameService {

    @NonNull
    private final Brain brain;

    @NonNull
    private final ApplicationProperties applicationProperties;

    @NonNull
    private final GameProperties gameProperties;

    @NonNull
    private final GameClient gameClient;

    @Getter
    @Setter
    private UUID lobbyId;

    // Improve: find a better way to keep track of already processed requests
    private final Set<RequestId> alreadyProcessedRequestIds = new HashSet<>();

    @EventListener(ApplicationReadyEvent.class)
    public void joinGame() {
        // subscription is hardcoded
        this.lobbyId = gameClient.joinLobby();
    }

    public void onGameUpdate(final EventType eventType, final Object payload) {
        switch (eventType) {
            case PLAYER_JOINED -> log.info("New player joined my game!");
            case SETUP_GAME -> handleSettingUpGame((GameConfigEvent) payload);
            case BOAT_PLACED -> log.info("wait... i know where his boats are?!");
            case START_PLAYING -> log.info("Ready to play!");
            case TAKE_TURN -> handleTakingTurn((TakeTurnEvent) payload);
            case PLAYER_ATTACKED -> log.info("cool attack!");
            case GAME_ENDED -> log.info("GG");
        }
    }

    private void handleSettingUpGame(final GameConfigEvent event) {
        gameProperties.createGameConfig(event);
        List<BoatInformation> boatInformation = brain.chooseBoatCoordinates(event);

        boatInformation.forEach(info -> placeBoat(info));
    }

    private void placeBoat(final BoatInformation boatInformation) {
        boolean successful = gameClient.placeCoordinate(boatInformation.baseCoordinate(), boatInformation.boatType(),
                boatInformation.boatDirection());
        if (!successful) {
            throw new RuntimeException("Failed to place the boat!");
        }
    }

    private void handleTakingTurn(final TakeTurnEvent event) {
        if (event.gameId().equals(applicationProperties.getGameId()) &&
                event.playerId().equals(applicationProperties.getPlayerId())) {
            AttackTurnAction attackTurnAction = brain.attack();
            gameClient.attack(attackTurnAction.coordinate());
        } else {
            log.info("Not my turn to attack.");
        }
    }

    public void onGameUpdate(GameUpdate gameUpdate) {
        // Improve: use this to get updates from the bots
//        GameDto gameDto = gameUpdate.gameDto();
//        if (gameDto.status() == GameStatus.NOT_STARTED) {
//            log.info("Not taking any action, game is not started yet...");
//            return;
//        }
//        if (gameDto.status() == GameStatus.FINISHED || gameDto.status() == GameStatus.DELETED) {
//            log.info("Game is finished, shutting down...");
//            shutDownService.shutDown();
//        }
//
//        gameDto.state().currentRequests().stream()
//                .filter(request -> !alreadyProcessedRequestIds.contains(request.requestId()))
//                .filter(request -> request.playerId().equals(playerId))
//                .forEach(this::processRequest);
    }

    private void processRequest(PlayRequest playRequest) {
//        log.info("Processing request: {}", playRequest);
//        alreadyProcessedRequestIds.add(playRequest.requestId());
//
//        GameAction decision = brain.decide(playRequest.possibleActions());
//
//        Move move = new Move(playerId, playRequest.requestId(), decision);
//        gameClient.play(move);
    }


}
