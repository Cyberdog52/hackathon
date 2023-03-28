package ch.zuehlke.fullstack.hackathon.statemachine;

import ch.zuehlke.common.shared.action.lobby.PlayerJoinAction;
import ch.zuehlke.common.shared.action.setup.PlaceBoatAction;
import ch.zuehlke.fullstack.hackathon.model.Game;
import ch.zuehlke.fullstack.hackathon.model.Lobby;
import ch.zuehlke.fullstack.hackathon.model.game.GameEvent;
import ch.zuehlke.fullstack.hackathon.model.game.state.GameState;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineEventResult;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MyStateMachine {


    @NonNull
    public final StateMachine<GameState, GameEvent> stateMachine;

    public Flux<StateMachineEventResult<ch.zuehlke.common.GameState, GameEvent>> playerJoined(
            final PlayerJoinAction request) {
        Message message = MessageBuilder.withPayload(GameEvent.PLAYER_JOINED)
                .setHeader(Header.PLAYER_JOINED.name(), request)
                .build();

        return sendEventMessage(message);
    }

    public Flux<StateMachineEventResult<ch.zuehlke.common.GameState, GameEvent>> placeBoat(
            final PlaceBoatAction request) {
        Message message = MessageBuilder.withPayload(GameEvent.PLACE_BOAT)
                .setHeader(Header.PLACE_BOAT.name(), request)
                .build();

        return sendEventMessage(message);
    }

    private Flux<StateMachineEventResult<ch.zuehlke.common.GameState, GameEvent>> sendEventMessage(final Message message) {
        return stateMachine.sendEvent(Mono.just(message));
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
