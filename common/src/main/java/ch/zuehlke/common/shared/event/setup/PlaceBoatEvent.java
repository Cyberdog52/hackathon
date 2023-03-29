package ch.zuehlke.common.shared.event.setup;

import ch.zuehlke.common.Coordinate;
import ch.zuehlke.common.shared.action.setup.BoatType;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record PlaceBoatEvent(
        UUID playerId,
        List<Coordinate> coordinates,
        boolean successful,
        UUID gameId,
        BoatType boatType
) {
    public String getType() {
        return "PlaceBoatEvent";
    }
}