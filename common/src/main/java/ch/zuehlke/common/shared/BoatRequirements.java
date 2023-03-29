package ch.zuehlke.common.shared;

import ch.zuehlke.common.shared.action.setup.BoatType;
import lombok.Builder;

import java.util.List;

@Builder
public record BoatRequirements(
        List<BoatType> boatTypes
) {

}
