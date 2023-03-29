package ch.zuehlke.fullstack.hackathon.model.factory;

import ch.zuehlke.fullstack.hackathon.model.game.GameConfig;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GameConfigFactory {

    public static GameConfig createSimpleGameConfig() {
        return GameConfig.builder()
                .maxNumberOfBoats(1)
                .mapHeight(1)
                .mapWidth(1)
                .build();
    }

    public static GameConfig createNormalGameConfig() {
        return GameConfig.builder()
            .maxNumberOfBoats(5)
            .mapHeight(10)
            .mapWidth(10)
            .build();
    }

}
