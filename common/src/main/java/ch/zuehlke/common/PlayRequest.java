package ch.zuehlke.common;

import java.util.Set;

public record PlayRequest(PlayerId playerId, RequestId requestId, GameId gameId, boolean attacker, Board board,
                          Set<GameAction> possibleActions) {

    public PlayRequest(PlayerId playerId, GameId gameId, boolean attacker, Board board, Set<GameAction> possibleActions) {
        this(playerId, new RequestId(), gameId, attacker, board, possibleActions);
    }
}
