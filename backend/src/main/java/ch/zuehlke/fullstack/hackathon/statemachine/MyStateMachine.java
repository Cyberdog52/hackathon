package ch.zuehlke.fullstack.hackathon.statemachine;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyStateMachine implements LobbyStateMachine {
    @Override
    public void playerJoined() {

    }

//    @NonNull
//    private final StateMachine<GameState, GameEvent> stateMachine;
//
//    @Override
//    public void playerJoined() {
//        sendEvent(GameEvent.PLAYER_JOINED);
//    }
//
//    private void sendEvent(final GameEvent gameEvent) {
//        boolean success = stateMachine.sendEvent(gameEvent);
//        if (!success) throw new RuntimeException("Failed to send GameEvent: " + gameEvent);
//    }

}
