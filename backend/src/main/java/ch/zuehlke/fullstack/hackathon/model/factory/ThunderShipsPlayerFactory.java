package ch.zuehlke.fullstack.hackathon.model.factory;

import ch.zuehlke.fullstack.hackathon.model.GameMap;
import ch.zuehlke.fullstack.hackathon.model.ThunderShipsPlayer;
import ch.zuehlke.fullstack.hackathon.model.game.GameConfig;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class ThunderShipsPlayerFactory {

    public static ThunderShipsPlayer createPlayer(final UUID playerId, final GameConfig gameConfig) {
        GameMap map = GameMapFactory.createSimpleMap(gameConfig);
        ThunderShipsPlayer player = ThunderShipsPlayer.builder()
                .id(playerId)
                .gameMap(map)
                .build();
        return player;
    }

}
