package ch.zuehlke.common;

import ch.zuehlke.tablut.Coordinates;

import java.util.Set;

public record PlayRequest(PlayerId playerId, RequestId requestId, Set<GameAction> possibleActions) {

    public PlayRequest(PlayerId playerId, Set<GameAction> possibleActions) {
        this(playerId, new RequestId(), possibleActions);
    }
}
