package ch.zuehlke.fullstack.hackathon.model.factory;

import ch.zuehlke.common.shared.BoatRequirements;
import ch.zuehlke.common.shared.action.setup.BoatType;
import ch.zuehlke.fullstack.hackathon.model.game.GameConfig;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class GameConfigFactory {

    public static GameConfig createSimpleGameConfig() {
        return GameConfig.builder()
                .maxNumberOfBoats(1)
                .mapHeight(1)
                .mapWidth(1)
                .boatRequirements(BoatRequirements.builder()
                        .boatTypes(List.of(BoatType.SMALL))
                        .build())
                .build();
    }

    public static GameConfig createNormalGameConfig() {
        return GameConfig.builder()
                .maxNumberOfBoats(3)
                .mapHeight(5)
                .mapWidth(5)
                .boatRequirements(BoatRequirements.builder()
                        .boatTypes(List.of(BoatType.LARGE, BoatType.SMALL, BoatType.MEDIUM))
                        .build())
            .build();
    }

}
