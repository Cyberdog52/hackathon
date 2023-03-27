package ch.zuehlke.common.shared.event.setup;

import ch.zuehlke.common.Coordinate;

import java.util.UUID;

record PlaceBoatEvent(
        UUID playerId,
        Coordinate coordinate,
        boolean successful
) {

}