package ch.zuehlke.fullstack.hackathon.statemachine.action.playing;

import ch.zuehlke.common.shared.action.playing.AttackTurnAction;
import ch.zuehlke.fullstack.hackathon.model.Game;
import ch.zuehlke.fullstack.hackathon.model.game.GameEvent;
import ch.zuehlke.fullstack.hackathon.model.game.state.GameState;
import ch.zuehlke.fullstack.hackathon.service.orchestrator.PlayingOrchestrator;
import ch.zuehlke.fullstack.hackathon.statemachine.Header;
import ch.zuehlke.fullstack.hackathon.statemachine.Variable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.MessageHeaders;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static ch.zuehlke.fullstack.hackathon.model.game.GameEvent.ALL_BOATS_DESTROYED;

@Component
@RequiredArgsConstructor
public class Attack {

    @NonNull
    private final PlayingOrchestrator playingOrchestrator;

    public Action<GameState, GameEvent> attack() {
        return context -> {
            var stateMachine = context.getStateMachine();

            MessageHeaders messageHeaders = context.getMessageHeaders();
            AttackTurnAction action = (AttackTurnAction) messageHeaders.get(Header.ATTACK_TURN.name());
            Game storedGame = (Game) stateMachine.getExtendedState().getVariables().get(Variable.GAME_ID);
            GameState currentState = stateMachine.getState().getId();
            UUID firstPlayerId = (UUID) stateMachine.getExtendedState().getVariables()
                .get(Variable.FIRST_PLAYER_ID);

            Game updatedGame = playingOrchestrator.attack(storedGame.gameId(), action.playerId(),
                action.coordinate(), currentState, firstPlayerId, stateMachine);

            stateMachine.getExtendedState().getVariables().put(Variable.GAME_ID, updatedGame);

            // check if all boats placed
            if (updatedGame.allBoatsDestroyed()) {
                stateMachine.sendEvent(ALL_BOATS_DESTROYED);
            }
        };
    }
}
