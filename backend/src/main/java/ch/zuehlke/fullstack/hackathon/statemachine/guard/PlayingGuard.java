package ch.zuehlke.fullstack.hackathon.statemachine.guard;

import ch.zuehlke.fullstack.hackathon.model.Game;
import ch.zuehlke.fullstack.hackathon.model.game.GameEvent;
import ch.zuehlke.fullstack.hackathon.model.game.state.GameState;
import ch.zuehlke.fullstack.hackathon.statemachine.Variable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlayingGuard {

    public Guard<GameState, GameEvent> playingGuard() {
        return context -> {
            Game game = (Game) context.getExtendedState().getVariables().get(Variable.GAME_ID);
            return canStartPlayingGame(game);
        };
    }

    private boolean canStartPlayingGame(final Game game) {
        // check all boats are placed
        return game.allBoatsPlaced();
    }

}
