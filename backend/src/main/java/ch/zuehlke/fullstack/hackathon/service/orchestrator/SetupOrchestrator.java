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
import ch.zuehlke.fullstack.hackathon.statemachine.MyStateMachine;
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

    @NonNull
    private final MyStateMachine stateMachine;

    public PlaceBoatEvent placeBoat(final UUID gameId, final UUID playerId, final Coordinate coordinate) {
        Game game = gameService.get(gameId);
        // check if you can place a boat
        Boat boat = Boat.builder()
                .destroyed(false)
                .coordinate(coordinate)
                .build();
        boolean boatPlaced = game.addBoat(playerId, boat);
        if (boatPlaced) {
            stateMachine.playerAddedBoat(game);
        }

        PlaceBoatEvent placeBoatEvent = mapToPlaceBoatEvent(playerId, boat, boatPlaced);
        notificationService.notifyBoatPlaced(placeBoatEvent, gameId);
        return placeBoatEvent;
    }

    public void initialiseGame() {
        Lobby lobby = stateMachine.getLobby();
        Game simpleGame = GameFactory.createSimpleGame(lobby);
        gameService.create(simpleGame);

        GameConfigEvent gameConfigEvent = GameConfigEventMapper.mapToGameConfigEvent(simpleGame);
        notificationService.notifyGameInitialised(gameConfigEvent);
    }

}
