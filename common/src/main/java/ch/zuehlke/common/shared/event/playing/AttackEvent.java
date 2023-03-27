package ch.zuehlke.common.shared.event.playing;

import ch.zuehlke.common.Coordinate;

import java.util.UUID;

public record AttackEvent(
        AttackStatus status,
        UUID attackingPlayerId,
        Coordinate coordinate
) {
    public enum AttackStatus {
        HIT,
        MISS,
        IGNORED
    }
}