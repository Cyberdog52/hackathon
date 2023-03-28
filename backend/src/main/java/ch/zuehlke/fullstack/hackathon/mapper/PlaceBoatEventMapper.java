package ch.zuehlke.fullstack.hackathon.mapper;

import ch.zuehlke.common.shared.event.setup.PlaceBoatEvent;
import ch.zuehlke.fullstack.hackathon.model.Boat;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class PlaceBoatEventMapper {

    public static PlaceBoatEvent mapToPlaceBoatEvent(final UUID playerId, final Boat boat, final boolean successful) {
        return PlaceBoatEvent.builder()
                //todo add gameId
                .coordinate(boat.getCoordinate())
                .successful(successful)
                .playerId(playerId)
                .build();
    }

}
