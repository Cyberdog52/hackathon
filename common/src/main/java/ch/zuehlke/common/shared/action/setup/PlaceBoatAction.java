package ch.zuehlke.common.shared.action.setup;

import ch.zuehlke.common.Coordinate;

import java.util.UUID;

public record PlaceBoatAction (
        UUID playerId,
        Coordinate coordinate,
        UUID gameId
        ) {
}
