package ch.zuehlke.common;

import ch.zuehlke.tablut.Coordinates;

import java.util.Set;

public record PlayRequest(PlayerId playerId, RequestId requestId, Set<GameAction> possibleActions) {

    public PlayRequest(PlayerId playerId) {
        this(playerId, new RequestId(), Set.of(new GameAction(new Coordinates(0,0),new Coordinates(0,0))));
    }
}
