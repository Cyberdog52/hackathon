package ch.zuehlke.challenge.bot.service;

import ch.zuehlke.challenge.bot.model.State;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static ch.zuehlke.challenge.bot.model.State.*;

@Service
@RequiredArgsConstructor
public class GameState {

    private State currentState = State.WAITING_TO_JOIN_GAME;

    public void joinedGame() {
        currentState = WAITING_FOR_GAME_TO_BEGIN_SETTING_UP;
    }

    public void settingUpGame() {
        currentState = SETTING_UP_GAME;
    }

    public void gameSetup() {
        currentState = WAITING_FOR_TURN;
    }

    public void takeTurn() {
        currentState = EXECUTING_TURN;
    }

    public void turnTaken() {
        currentState = WAITING_FOR_TURN;
    }

    public void gameOver() {
        currentState = END_OF_GAME;
    }

}
