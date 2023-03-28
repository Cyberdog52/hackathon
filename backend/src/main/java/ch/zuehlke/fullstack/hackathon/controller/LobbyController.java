package ch.zuehlke.fullstack.hackathon.controller;

import ch.zuehlke.common.shared.action.lobby.PlayerJoinAction;
import ch.zuehlke.common.shared.event.lobby.PlayerJoinEvent;
import ch.zuehlke.fullstack.hackathon.model.game.GameEvent;
import ch.zuehlke.fullstack.hackathon.model.game.state.GameState;
import ch.zuehlke.fullstack.hackathon.service.orchestrator.LobbyOrchestrator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static ch.zuehlke.fullstack.hackathon.model.game.GameEvent.PLAYER_JOINED;

@RestController
@RequestMapping("/api/lobby")
@RequiredArgsConstructor
public class LobbyController {

    @NonNull
    private final LobbyOrchestrator lobbyOrchestrator;

    @NonNull
    private final StateMachine<GameState, GameEvent> stateMachine;

    @PostMapping("/join")
    public ResponseEntity<PlayerJoinEvent> join(final PlayerJoinAction request) {
        Message message = MessageBuilder.withPayload(PLAYER_JOINED)
                .setHeader("info", request)
                .build();

        Flux<ResponseEntity<PlayerJoinEvent>> event = stateMachine.sendEvent(Mono.just(message))
                .map(result -> result.toString())
                .map(str -> ResponseEntity.ok().build());
        ResponseEntity responseEntity = event.blockLast();
        return responseEntity;
    }

}
