package ch.zuehlke.common.shared.event.setup;

import ch.zuehlke.common.Coordinate;
import lombok.Builder;

import java.util.UUID;

@Builder
public record PlaceBoatEvent(
        UUID playerId,
        Coordinate coordinate,
        boolean successful
) {

}