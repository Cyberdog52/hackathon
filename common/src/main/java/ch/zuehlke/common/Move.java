package ch.zuehlke.common;

import java.util.Optional;

public record Move(PlayerId playerId, RequestId requestId, GameAction action) {

    public static Optional<PlayerId> getWinningPlayer(Move move1, Move move2) {
        if (move1.action.beats(move2.action)) {
            return Optional.of(move1.playerId);
        }
        if (move2.action.beats(move1.action)) {
            return Optional.of(move2.playerId);
        }
        return Optional.empty();
    }
}
