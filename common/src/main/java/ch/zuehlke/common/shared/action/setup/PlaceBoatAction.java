package ch.zuehlke.common.shared.action.setup;

import ch.zuehlke.common.Coordinate;
import lombok.Builder;

import java.util.UUID;

@Builder
public record PlaceBoatAction(
        UUID playerId,
        Coordinate coordinate,
        UUID gameId,
        BoatType boatType,
        BoatDirection boatDirection
) {
}
