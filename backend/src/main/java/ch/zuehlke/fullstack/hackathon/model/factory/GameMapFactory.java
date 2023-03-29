package ch.zuehlke.fullstack.hackathon.model.factory;

import ch.zuehlke.fullstack.hackathon.model.GameMap;
import ch.zuehlke.fullstack.hackathon.model.game.GameConfig;
import lombok.experimental.UtilityClass;

import java.util.HashSet;

@UtilityClass
public class GameMapFactory {

    public static GameMap createSimpleMap(final GameConfig gameConfig) {
        return GameMap.builder()
                .boats(new HashSet<>()) // mutable set
                .height(gameConfig.mapHeight())
                .width(gameConfig.mapWidth())
                .build();
    }

}
