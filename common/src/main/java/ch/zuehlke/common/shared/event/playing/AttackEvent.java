package ch.zuehlke.common.shared.event.playing;

import ch.zuehlke.common.Coordinate;
import lombok.Builder;

import java.util.UUID;

@Builder
public record AttackEvent(
        AttackStatus status,
        UUID attackingPlayerId,
        Coordinate coordinate,
        UUID gameId
        ) {
    public enum AttackStatus {
        HIT,
        MISS,
        IGNORED
    }
}