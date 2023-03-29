package ch.zuehlke.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Improve: Split this into DTO and domain object
public record GameState(Set<PlayRequest> currentRequests, List<Move> moves, Board board) {

    public GameState(Set<PlayRequest> currentRequests, List<Move> moves) {
        this(currentRequests, moves, Board.createInitialBoard());
    }

    public GameState() {
        this(new HashSet<>(), new ArrayList<>(), Board.createInitialBoard());
    }


}
