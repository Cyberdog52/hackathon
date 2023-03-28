package ch.zuehlke.fullstack.hackathon.service.orchestrator;

import ch.zuehlke.common.Coordinate;
import ch.zuehlke.common.shared.event.setup.GameConfigEvent;
import ch.zuehlke.common.shared.event.setup.PlaceBoatEvent;
import ch.zuehlke.fullstack.hackathon.mapper.GameConfigEventMapper;
import ch.zuehlke.fullstack.hackathon.model.Boat;
import ch.zuehlke.fullstack.hackathon.model.Game;
import ch.zuehlke.fullstack.hackathon.model.Lobby;
import ch.zuehlke.fullstack.hackathon.model.factory.GameFactory;
import ch.zuehlke.fullstack.hackathon.service.GameService;
import ch.zuehlke.fullstack.hackathon.service.NotificationService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static ch.zuehlke.fullstack.hackathon.mapper.PlaceBoatEventMapper.mapToPlaceBoatEvent;

@Service
@RequiredArgsConstructor
public class SetupOrchestrator {

    @NonNull
    private final NotificationService notificationService;

    @NonNull
    private final GameService gameService;

    public Game placeBoat(final UUID gameId, final UUID playerId, final Coordinate coordinate) {
        Game game = gameService.get(gameId);
        // check if you can place a boat
        Boat boat = Boat.builder()
                .destroyed(false)
                .coordinate(coordinate)
                .build();
        boolean boatPlaced = game.addBoat(playerId, boat);
        if (!boatPlaced) {
            throw new RuntimeException(String.format("Unable to place boat for gameId %s, playerId %s and coordinates %s",
                    gameId, playerId, coordinate));
        }
        PlaceBoatEvent placeBoatEvent = mapToPlaceBoatEvent(playerId, boat, boatPlaced);
        notificationService.notifyBoatPlaced(placeBoatEvent, gameId);
        return game;
    }

    public Game initialiseGame(final Lobby lobby) {
        Game simpleGame = GameFactory.createSimpleGame(lobby);
        gameService.create(simpleGame);

        GameConfigEvent gameConfigEvent = GameConfigEventMapper.mapToGameConfigEvent(simpleGame);
        notificationService.notifyGameInitialised(gameConfigEvent);
        return simpleGame;
    }

    public void allBoatsPlaced(final Game game) {

    }

}