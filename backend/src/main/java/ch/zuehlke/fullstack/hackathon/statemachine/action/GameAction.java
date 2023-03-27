package ch.zuehlke.fullstack.hackathon.statemachine.action;

import ch.zuehlke.fullstack.hackathon.model.game.GameEvent;
import ch.zuehlke.fullstack.hackathon.model.game.state.GameState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import static ch.zuehlke.fullstack.hackathon.model.game.state.GameState.*;

@Component
@Slf4j
public class GameAction implements Action<GameState, GameEvent> {
    @Override
    public void execute(StateContext<GameState, GameEvent> context) {
        if (context.getTarget().getId() == END) {
            log.info("Game ending!");
            //emit winner
        }
        if (context.getSource().getId() == LOBBY && context.getTarget().getId() == SETUP) {
            log.info("Game transitioning to setup");
            //emit game config
        }
    }
}
