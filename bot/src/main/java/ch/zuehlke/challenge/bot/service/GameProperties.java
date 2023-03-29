package ch.zuehlke.challenge.bot.service;

import ch.zuehlke.challenge.bot.model.GameConfig;
import ch.zuehlke.common.shared.event.setup.GameConfigEvent;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
@NonNull
public class GameProperties {

    @Getter
    private GameConfig gameConfig;

    public void createGameConfig(final GameConfigEvent gameConfig) {
        this.gameConfig = GameConfig.builder()
                .mapHeight(gameConfig.mapSizeY())
                .mapWidth(gameConfig.mapSizeX())
                .numberOfBoats(gameConfig.numberOfBoats())
                .boatRequirements(gameConfig.boatRequirements())
                .build();
    }

}
