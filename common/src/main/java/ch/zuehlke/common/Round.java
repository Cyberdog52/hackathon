package ch.zuehlke.common;

import java.util.List;

public record Round(List<Move> moves) {

    public Round(Move move1, Move move2) {
        this(List.of(move1, move2));
    }
}
