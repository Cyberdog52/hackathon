package ch.zuehlke.fullstack.hackathon.statemachine.action;

import ch.zuehlke.fullstack.hackathon.model.game.GameEvent;
import ch.zuehlke.fullstack.hackathon.model.game.state.GameState;
import ch.zuehlke.fullstack.hackathon.service.orchestrator.LobbyOrchestrator;
import ch.zuehlke.fullstack.hackathon.statemachine.Header;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlayerJoinAction {

    @NonNull
    private final LobbyOrchestrator lobbyOrchestrator;

    public Action<GameState, GameEvent> playerJoin() {
        return context -> {
            MessageHeaders messageHeaders = context.getMessageHeaders();
            ch.zuehlke.common.shared.action.lobby.PlayerJoinAction action =
                    (ch.zuehlke.common.shared.action.lobby.PlayerJoinAction) messageHeaders
                            .get(Header.PLAYER_JOINED.name());

            lobbyOrchestrator.join(action.playerId(), action.gameId());
        };
    }

}
