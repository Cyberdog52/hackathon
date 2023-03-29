package ch.zuehlke.fullstack.hackathon.mapper;

import ch.zuehlke.common.shared.event.setup.GameConfigEvent;
import ch.zuehlke.fullstack.hackathon.model.Game;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GameConfigEventMapper {

    public static GameConfigEvent mapToGameConfigEvent(final Game game) {
        return GameConfigEvent.builder()
                .playerIds(game.players().stream().map(p -> p.id()).toList())
                .mapSizeX(game.gameConfig().mapWidth())
                .mapSizeY(game.gameConfig().mapHeight())
                .numberOfBoats(game.gameConfig().maxNumberOfBoats())
                .timeLimit(null)
                .gameId(game.gameId())
                .boatRequirements(game.gameConfig().boatRequirements())
                .build();
    }

}
