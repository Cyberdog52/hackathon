package ch.zuehlke.common;

import ch.zuehlke.tablut.Board;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Improve: Split this into DTO and domain object
public record TournamentState(Set<PlayRequest> currentRequests, List<Move> moves, Board board) {

    public TournamentState(Set<PlayRequest> currentRequests, List<Move> moves) {
        this(currentRequests, moves, Board.createInitialBoard());
    }

    public TournamentState() {
        this(new HashSet<>(), new ArrayList<>(), Board.createInitialBoard());
    }


}
