package ch.zuehlke.tablut;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GameState {

    private Board board;

    private List<GameAction> actionHistory;

    public GameState() {
        this.board = Board.createInitialBoard();
        this.actionHistory = new ArrayList<>();
    }

    public GameState(Board board, List<GameAction> actionHistory) {
        this.board = board;
        this.actionHistory = actionHistory;
    }

    public Set<GameAction> getPossibleActions() {
        return Set.of(new GameAction(new Coordinates(0, 3), new Coordinates(0, 0)));
    }
}
