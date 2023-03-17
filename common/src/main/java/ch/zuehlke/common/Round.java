package ch.zuehlke.common;

import java.util.List;

public record Round(List<Move> moves, PlayerId winner) {
    public static final int MAX_MOVES = 2;

    public Round(Move move1, Move move2) {
        this(List.of(move1, move2), Move.getWinningPlayer(move1, move2).orElse(null));
    }
}
