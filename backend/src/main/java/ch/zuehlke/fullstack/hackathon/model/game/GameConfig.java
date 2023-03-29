package ch.zuehlke.fullstack.hackathon.model.game;

import ch.zuehlke.common.shared.BoatRequirements;
import lombok.Builder;

@Builder
public record GameConfig(
        int mapWidth,
        int mapHeight,
        int maxNumberOfBoats,
        BoatRequirements boatRequirements
) {

}
