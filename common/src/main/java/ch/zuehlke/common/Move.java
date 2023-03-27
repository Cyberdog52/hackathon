package ch.zuehlke.common;

import java.util.Optional;

public record Move(PlayerId playerId, RequestId requestId, GameAction action) {

    public static Optional<PlayerId> getWinningPlayer(Move move1, Move move2) {
        return Optional.of(move1.playerId);
    }
}
