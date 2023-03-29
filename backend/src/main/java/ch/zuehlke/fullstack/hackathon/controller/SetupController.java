package ch.zuehlke.fullstack.hackathon.controller;

import ch.zuehlke.common.GameState;
import ch.zuehlke.common.shared.action.setup.PlaceBoatAction;
import ch.zuehlke.common.shared.event.setup.GameConfigEvent;
import ch.zuehlke.common.shared.event.setup.PlaceBoatEvent;
import ch.zuehlke.fullstack.hackathon.mapper.PlaceBoatEventMapper;
import ch.zuehlke.fullstack.hackathon.model.game.GameEvent;
import ch.zuehlke.fullstack.hackathon.statemachine.MyStateMachine;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.statemachine.StateMachineEventResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.statemachine.StateMachineEventResult.ResultType.DENIED;

@RestController
@RequestMapping("/api/setup")
@RequiredArgsConstructor
public class SetupController {

    @NonNull
    private final MyStateMachine stateMachine;

    @PostMapping("/placeBoat")
    public ResponseEntity<PlaceBoatEvent> placeBoat(final PlaceBoatAction request) {
        Flux<StateMachineEventResult<GameState, GameEvent>> resultFlux = stateMachine.placeBoat(request);
        StateMachineEventResult<GameState, GameEvent> result = resultFlux.blockFirst();

        boolean successful = result.getResultType().equals(DENIED);
        return ResponseEntity.ok()
                .body(PlaceBoatEventMapper.mapToPlaceBoatEvent(request.playerId(), request.coordinate(), successful));
    }

    @GetMapping("/config")
    public ResponseEntity<GameConfigEvent> config() {
        return ResponseEntity.ok().build();
    }

}
