package ch.zuehlke.common.shared.action.playing;

import ch.zuehlke.common.Coordinate;

import java.util.UUID;

public record AttackTurnAction(
        UUID playerId,
        Coordinate coordinate
) {

}
