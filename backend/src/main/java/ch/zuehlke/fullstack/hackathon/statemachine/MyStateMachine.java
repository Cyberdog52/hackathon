package ch.zuehlke.fullstack.hackathon.statemachine;

import ch.zuehlke.fullstack.hackathon.model.Game;
import ch.zuehlke.fullstack.hackathon.model.Lobby;
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
    public final StateMachine<GameState, GameEvent> stateMachine;

    @Override
    public void playerJoined(final Lobby lobby) {
        stateMachine.getExtendedState().getVariables().put(Variable.LOBBY_ID, lobby);
        sendEvent(GameEvent.PLAYER_JOINED);
    }

    public void playerAddedBoat(final Game game) {
        stateMachine.getExtendedState().getVariables().put(Variable.GAME_ID, game);
        sendEvent(GameEvent.PLACE_BOAT);
    }

    private void sendEvent(final GameEvent gameEvent) {
        boolean success = stateMachine.sendEvent(gameEvent);
        if (!success) throw new RuntimeException("Failed to send GameEvent: " + gameEvent);
    }

    public Lobby getLobby() {
        return (Lobby) stateMachine.getExtendedState().getVariables().get(Variable.LOBBY_ID);
    }

    public Game getGame() {
        return (Game) stateMachine.getExtendedState().getVariables().get(Variable.GAME_ID);
    }

}
