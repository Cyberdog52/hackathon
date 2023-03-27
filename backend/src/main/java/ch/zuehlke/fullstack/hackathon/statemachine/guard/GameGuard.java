package ch.zuehlke.fullstack.hackathon.statemachine.guard;

import ch.zuehlke.fullstack.hackathon.model.game.GameEvent;
import ch.zuehlke.fullstack.hackathon.model.game.state.GameState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

import java.util.List;

import static ch.zuehlke.fullstack.hackathon.model.game.state.GameState.PLAYING;
import static ch.zuehlke.fullstack.hackathon.model.game.state.GameState.SETUP;

@Component
@Slf4j
public class GameGuard implements Guard<GameState, GameEvent> {

    @Override
    public boolean evaluate(StateContext<GameState, GameEvent> context) {
        if (context.getTarget().getId() == SETUP) return toSetup();
        if (context.getTarget().getId() == PLAYING) return toPlaying();
        return true;
    }

    private boolean toSetup() {
        // check if the requirements for transitioning to the setup phase work.
        int numberOfPlayers = 2;
        int minNumberOfPlayers = 2;
        int maxNumberOfPlayers = 2;
        return numberOfPlayers >= minNumberOfPlayers && numberOfPlayers <= maxNumberOfPlayers;
    }

    private boolean toPlaying() {
        List<Integer> playersBoatsPlaced = List.of(5, 5);
        int minBoatsToPlace = 5;
        return playersBoatsPlaced.stream().allMatch(boatsPlaced -> boatsPlaced.equals(5));
    }

}
