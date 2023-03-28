package ch.zuehlke.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Round {

    private Map<String, Move> moves = new HashMap();

    public void addMove(Move move) {
        if (receivedBothMoves()) {
            throw new IllegalArgumentException("Both moves were already set.");
        }
        if (isPlayerAllowedToShoot(move.playerId())) {
            throw new IllegalArgumentException("Player did already submit a move");
        }
        moves.put(move.playerId(), move);
    }

    public boolean isPlayerAllowedToShoot(String playerId) {
        return Objects.nonNull(moves.get(playerId));
    }

    public boolean receivedBothMoves() {
        return moves.size() == 2;
    }
}
