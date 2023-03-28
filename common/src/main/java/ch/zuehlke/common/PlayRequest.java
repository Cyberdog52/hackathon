package ch.zuehlke.common;

import ch.zuehlke.tablut.Board;

import java.util.Set;

public record PlayRequest(PlayerId playerId, RequestId requestId, boolean attacker, Board board,
                          Set<GameAction> possibleActions) {

    public PlayRequest(PlayerId playerId, boolean attacker, Board board, Set<GameAction> possibleActions) {
        this(playerId, new RequestId(), attacker, board, possibleActions);
    }
}
