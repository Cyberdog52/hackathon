package ch.zuehlke.common.shared.event.setup;

import ch.zuehlke.common.Coordinate;

import java.util.UUID;

public record PlaceBoatEvent(
        UUID playerId,
        Coordinate coordinate,
        boolean successful
) {
    public String getType() {
        return "PlaceBoatEvent";
    }
}