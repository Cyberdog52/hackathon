package ch.zuehlke.fullstack.hackathon.statemachine.action.lobby;

import ch.zuehlke.fullstack.hackathon.model.Lobby;
import ch.zuehlke.fullstack.hackathon.model.game.GameEvent;
import ch.zuehlke.fullstack.hackathon.model.game.state.GameState;
import ch.zuehlke.fullstack.hackathon.service.orchestrator.LobbyOrchestrator;
import ch.zuehlke.fullstack.hackathon.statemachine.Header;
import ch.zuehlke.fullstack.hackathon.statemachine.Variable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static ch.zuehlke.fullstack.hackathon.model.game.GameEvent.ALL_PLAYERS_JOINED;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlayerJoinAction {

    @NonNull
    private final LobbyOrchestrator lobbyOrchestrator;

    public Action<GameState, GameEvent> playerJoin() {
        return context -> {
            var stateMachine = context.getStateMachine();

            MessageHeaders messageHeaders = context.getMessageHeaders();
            ch.zuehlke.common.shared.action.lobby.PlayerJoinAction action =
                    (ch.zuehlke.common.shared.action.lobby.PlayerJoinAction) messageHeaders
                            .get(Header.PLAYER_JOINED.name());

            Lobby lobby = lobbyOrchestrator.join(action.playerId(), action.gameId());
            stateMachine.getExtendedState().getVariables().put(Variable.LOBBY_ID, lobby);

            if (lobby.canStartGame()) {
                Message message = MessageBuilder.withPayload(ALL_PLAYERS_JOINED).build();
                stateMachine.sendEvent(Mono.just(message)).blockLast();
            }
        };
    }

}
