package ch.zuehlke.fullstack.hackathon.mapper;

import ch.zuehlke.common.Coordinate;
import ch.zuehlke.common.shared.event.setup.PlaceBoatEvent;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class PlaceBoatEventMapper {

    public static PlaceBoatEvent mapToPlaceBoatEvent(final UUID playerId, final Coordinate coordinate, final boolean successful) {
        return PlaceBoatEvent.builder()
                //todo add gameId
                .coordinate(coordinate)
                .successful(successful)
                .playerId(playerId)
                .build();
    }

}
