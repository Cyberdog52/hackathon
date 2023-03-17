package ch.zuehlke.common;

import java.util.Set;

public record PlayRequest(PlayerId playerId, RequestId requestId, Set<GameAction> possibleActions) {

    public PlayRequest(PlayerId playerId) {
        this(playerId, new RequestId(), Set.of(GameAction.values()));
    }
}
