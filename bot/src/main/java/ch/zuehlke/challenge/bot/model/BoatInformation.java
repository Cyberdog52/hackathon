package ch.zuehlke.challenge.bot.model;

import ch.zuehlke.common.Coordinate;
import ch.zuehlke.common.shared.action.setup.BoatDirection;
import ch.zuehlke.common.shared.action.setup.BoatType;
import lombok.Builder;

@Builder
public record BoatInformation(
        BoatType boatType,
        BoatDirection boatDirection,
        Coordinate baseCoordinate
) {

}
