package ch.zuehlke.fullstack.hackathon.mapper;

import ch.zuehlke.common.Coordinate;
import ch.zuehlke.common.shared.event.setup.PlaceBoatEvent;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.UUID;

@UtilityClass
public class PlaceBoatEventMapper {

    public static PlaceBoatEvent mapToPlaceBoatEvent(final UUID playerId, final List<Coordinate> coordinates, final boolean successful) {
        return PlaceBoatEvent.builder()
                .coordinates(coordinates)
                .successful(successful)
                .playerId(playerId)
                .build();
    }

}
