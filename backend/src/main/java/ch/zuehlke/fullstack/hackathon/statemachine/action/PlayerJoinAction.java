package ch.zuehlke.fullstack.hackathon.statemachine.action;

import ch.zuehlke.fullstack.hackathon.model.game.GameEvent;
import ch.zuehlke.fullstack.hackathon.model.game.state.GameState;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;
import org.springframework.statemachine.action.Action;

@UtilityClass
@Slf4j
public class PlayerJoinAction {

    public static Action<GameState, GameEvent> playerJoinAction() {
        return context -> {
            MessageHeaders messageHeaders = context.getMessageHeaders();
            PlayerJoinAction action = (PlayerJoinAction) messageHeaders.get(ch.zuehlke.fullstack.hackathon.model.Action.PLAYER_JOINED.name());
            context.getExtendedState()
                    .getVariables()
                    .put("deployed", true);
        };
    }

}
