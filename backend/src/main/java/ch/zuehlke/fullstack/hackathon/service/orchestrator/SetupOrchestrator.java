package ch.zuehlke.fullstack.hackathon.service.orchestrator;

import ch.zuehlke.common.Coordinate;
import ch.zuehlke.common.GamePlayingAction;
import ch.zuehlke.common.shared.action.setup.BoatDirection;
import ch.zuehlke.common.shared.action.setup.BoatType;
import ch.zuehlke.common.shared.event.playing.TakeTurnEvent;
import ch.zuehlke.common.shared.event.setup.GameConfigEvent;
import ch.zuehlke.common.shared.event.setup.PlaceBoatEvent;
import ch.zuehlke.fullstack.hackathon.mapper.GameConfigEventMapper;
import ch.zuehlke.fullstack.hackathon.model.Boat;
import ch.zuehlke.fullstack.hackathon.model.Game;
import ch.zuehlke.fullstack.hackathon.model.Lobby;
import ch.zuehlke.fullstack.hackathon.model.ThunderShipsPlayer;
import ch.zuehlke.fullstack.hackathon.model.factory.GameFactory;
import ch.zuehlke.fullstack.hackathon.service.GameService;
import ch.zuehlke.fullstack.hackathon.service.NotificationService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static ch.zuehlke.fullstack.hackathon.mapper.PlaceBoatEventMapper.mapToPlaceBoatEvent;
import static ch.zuehlke.fullstack.hackathon.model.Boat.determineBoatCoordinates;

@Service
@RequiredArgsConstructor
public class SetupOrchestrator {

    @NonNull
    private final NotificationService notificationService;

    @NonNull
    private final GameService gameService;

    public Game placeBoat(final UUID gameId, final UUID playerId, final Coordinate coordinate, final BoatType boatType,
                          final BoatDirection boatDirection, final UUID boatId) {
        Game game = gameService.get(gameId);

        List<Coordinate> boatCoordinates = determineBoatCoordinates(boatType, boatDirection, coordinate);
        if (!Boat.validBoatCoordinates(boatCoordinates, game.gameConfig().mapHeight(), game.gameConfig().mapWidth())) {
            throw new RuntimeException("Boat coordinates are not valied!");
        }

        // check if you can place a boat
        Boat boat = Boat.builder()
                .destroyed(false)
                .coordinates(determineBoatCoordinates(boatType, boatDirection, coordinate))
                .boatId(boatId)
                .hitCoordinates(new HashSet<>())
                .build();
        boolean boatPlaced = game.addBoat(playerId, boat);

        if (!boatPlaced) {
            throw new RuntimeException(String.format("Unable to place boat for gameId %s, playerId %s and coordinate %s",
                    gameId, playerId, coordinate));
        }
        PlaceBoatEvent placeBoatEvent = mapToPlaceBoatEvent(playerId, boat.getCoordinates(), boatPlaced);
        notificationService.notifyBoatPlaced(placeBoatEvent, gameId);
        return game;
    }

    public Game initialiseGame(final Lobby lobby) {
        Game simpleGame = GameFactory.createSimpleGame(lobby);
        gameService.create(simpleGame);

        GameConfigEvent gameConfigEvent = GameConfigEventMapper.mapToGameConfigEvent(simpleGame);
        notificationService.notifyGameInitialised(gameConfigEvent);
        notificationService.notifySpectatorGameInitialised(gameConfigEvent);
        return simpleGame;
    }

    public UUID allBoatsPlaced(final Game game) {
        ThunderShipsPlayer firstPlayer = game.players().stream()
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("No player assigned to game"));

        TakeTurnEvent takeTurnEvent = TakeTurnEvent.builder()
            .gameId(game.gameId())
            .playerId(firstPlayer.id())
            .actions(List.of(GamePlayingAction.ATTACK))
            .build();

        notificationService.notifyGameStarted(takeTurnEvent); // can replace with action from playing to playing_player_1
        notificationService.notifySpectatorGameStarted(takeTurnEvent); // can replace with action from playing to playing_player_1
        return firstPlayer.id();
    }

}
