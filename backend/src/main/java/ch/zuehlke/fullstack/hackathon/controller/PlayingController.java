package ch.zuehlke.fullstack.hackathon.controller;

import ch.zuehlke.common.GameState;
import ch.zuehlke.common.shared.action.playing.AttackTurnAction;
import ch.zuehlke.common.shared.event.playing.AttackEvent;
import ch.zuehlke.common.shared.event.playing.AttackEvent.AttackStatus;
import ch.zuehlke.fullstack.hackathon.mapper.AttackEventMapper;
import ch.zuehlke.fullstack.hackathon.model.game.GameEvent;
import ch.zuehlke.fullstack.hackathon.statemachine.MyStateMachine;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.statemachine.StateMachineEventResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.statemachine.StateMachineEventResult.ResultType.DENIED;

@RestController()
@RequestMapping("/api/playing")
@RequiredArgsConstructor
public class PlayingController {

    @NonNull
    private final MyStateMachine stateMachine;

    @PostMapping("/attack")
    public ResponseEntity<AttackEvent> attack(final AttackTurnAction request) {
        throw new NotImplementedException("Attacks should not be made through POST!");
//        Flux<StateMachineEventResult<GameState, GameEvent>> resultFlux = stateMachine.attack(request);
//        StateMachineEventResult<GameState, GameEvent> result = resultFlux.blockFirst();
//
//
//        return ResponseEntity.ok()
//            .body(AttackEventMapper.mapToAttackEvent(request.playerId(), request.coordinate(), AttackStatus.MISS,
//                request.gameId()));
    }

}
