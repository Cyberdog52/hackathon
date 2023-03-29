package ch.zuehlke.common.shared.action.playing;

import ch.zuehlke.common.Coordinate;
import lombok.Builder;

import java.util.UUID;

@Builder
public record AttackTurnAction(
        UUID playerId,
        Coordinate coordinate,
        UUID gameId
) {

}
