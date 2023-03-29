package ch.zuehlke.challenge.bot.model;

import ch.zuehlke.common.shared.BoatRequirements;
import lombok.Builder;

@Builder
public record GameConfig(
        int mapHeight,
        int mapWidth,
        int numberOfBoats,
        BoatRequirements boatRequirements
) {

}
