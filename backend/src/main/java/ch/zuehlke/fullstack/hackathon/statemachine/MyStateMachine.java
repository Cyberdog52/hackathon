package ch.zuehlke.fullstack.hackathon.statemachine;

import ch.zuehlke.fullstack.hackathon.model.game.GameEvent;
import ch.zuehlke.fullstack.hackathon.model.game.state.GameState;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyStateMachine implements LobbyStateMachine {

    @NonNull
    private final StateMachine<GameState, GameEvent> stateMachine;

    @Override
    public void playerJoined() {
        sendEvent(GameEvent.PLAYER_JOINED);
    }

    private void sendEvent(final GameEvent gameEvent) {
        boolean success = stateMachine.sendEvent(gameEvent);
        if (!success) throw new RuntimeException("Failed to send GameEvent: " + gameEvent);
    }

}
