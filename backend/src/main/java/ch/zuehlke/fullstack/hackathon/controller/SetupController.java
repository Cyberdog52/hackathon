package ch.zuehlke.fullstack.hackathon.controller;

import ch.zuehlke.common.GameState;
import ch.zuehlke.common.shared.action.setup.PlaceBoatAction;
import ch.zuehlke.common.shared.event.setup.GameConfigEvent;
import ch.zuehlke.common.shared.event.setup.PlaceBoatEvent;
import ch.zuehlke.fullstack.hackathon.mapper.PlaceBoatEventMapper;
import ch.zuehlke.fullstack.hackathon.model.Boat;
import ch.zuehlke.fullstack.hackathon.model.Game;
import ch.zuehlke.fullstack.hackathon.model.game.GameEvent;
import ch.zuehlke.fullstack.hackathon.statemachine.MyStateMachine;
import ch.zuehlke.fullstack.hackathon.statemachine.Variable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.statemachine.StateMachineEventResult;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.springframework.statemachine.StateMachineEventResult.ResultType.DENIED;

@RestController
@RequestMapping("/api/setup")
@RequiredArgsConstructor
public class SetupController {

    @NonNull
    private final MyStateMachine stateMachine;

    @PostMapping("/place-boat")
    public ResponseEntity<PlaceBoatEvent> placeBoat(@RequestBody final PlaceBoatAction request) {
        UUID boatId = UUID.randomUUID();
        Flux<StateMachineEventResult<GameState, GameEvent>> resultFlux = stateMachine.placeBoat(request, boatId);
        StateMachineEventResult<GameState, GameEvent> result = resultFlux.blockFirst();

        boolean successful = !result.getResultType().equals(DENIED);
        Game updatedGame = (Game) stateMachine.stateMachine.getExtendedState().getVariables().get(Variable.GAME_ID);
        Set<Boat> boats = updatedGame.players().stream()
                .filter(p -> p.id().equals(request.playerId()))
                .findFirst()
                .map(p -> p.gameMap().boats())
                .orElse(Collections.emptySet());

        Optional<Boat> boat = boats.stream().filter(b -> b.getBoatId().equals(boatId)).findFirst();

        if (boat.isPresent()) {
            return ResponseEntity.ok()
                    .body(PlaceBoatEventMapper.mapToPlaceBoatEvent(request.playerId(), boat.get().getCoordinates(), successful));
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/config")
    public ResponseEntity<GameConfigEvent> config() {
        return ResponseEntity.ok().build();
    }

}
