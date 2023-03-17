package ch.zuehlke.common;

public enum GameAction {
    ROCK, PAPER, SCISSORS;

    public boolean beats(GameAction action) {
        if (this == ROCK) {
            return action == SCISSORS;
        }
        if (this == PAPER) {
            return action == ROCK;
        }
        if (this == SCISSORS) {
            return action == PAPER;
        }
        return false;
    }
}
