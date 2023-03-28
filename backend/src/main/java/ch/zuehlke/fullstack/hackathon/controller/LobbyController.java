package ch.zuehlke.fullstack.hackathon.controller;

import ch.zuehlke.common.GameState;
import ch.zuehlke.common.shared.action.lobby.PlayerJoinAction;
import ch.zuehlke.common.shared.event.lobby.PlayerJoinEvent;
import ch.zuehlke.fullstack.hackathon.mapper.PlayerJoinEventMapper;
import ch.zuehlke.fullstack.hackathon.model.game.GameEvent;
import ch.zuehlke.fullstack.hackathon.statemachine.Header;
import ch.zuehlke.fullstack.hackathon.statemachine.MyStateMachine;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachineEventResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.statemachine.StateMachineEventResult.ResultType.DENIED;

@RestController
@RequestMapping("/api/lobby")
@RequiredArgsConstructor
public class LobbyController {

    @NonNull
    private final MyStateMachine myStateMachine;

    @PostMapping("/join")
    public ResponseEntity<PlayerJoinEvent> join(final PlayerJoinAction request) {
        Message message = MessageBuilder.withPayload(GameEvent.PLAYER_JOINED)
                .setHeader(Header.PLAYER_JOINED.name(), request)
                .build();

        Flux<StateMachineEventResult<GameState, GameEvent>> resultFlux = myStateMachine.stateMachine
                .sendEvent(Mono.just(message));
        StateMachineEventResult<GameState, GameEvent> result = resultFlux.blockFirst();

        if (result.getResultType().equals(DENIED)) {
            return ResponseEntity.internalServerError().build();
        }
        var lobby = myStateMachine.getLobby();
        return ResponseEntity.ok().body(PlayerJoinEventMapper.mapToPlayerJoinEvent(lobby.lobbyId(), request.playerId()));
    }

}
