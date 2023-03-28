package ch.zuehlke.fullstack.hackathon.model.factory;

import ch.zuehlke.fullstack.hackathon.model.Game;
import ch.zuehlke.fullstack.hackathon.model.Lobby;
import ch.zuehlke.fullstack.hackathon.model.ThunderShipsPlayer;
import ch.zuehlke.fullstack.hackathon.model.game.GameConfig;
import lombok.experimental.UtilityClass;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@UtilityClass
public class GameFactory {

    public static Game createSimpleGame(final Lobby lobby) {
        GameConfig gameConfig = GameConfigFactory.createSimpleGameConfig();
        final Set<ThunderShipsPlayer> players = createPlayers(lobby.playerIds(), gameConfig);
        return Game.builder()
                .gameId(lobby.lobbyId())
                .players(players)
                .gameConfig(gameConfig)
                .build();
    }

    private Set<ThunderShipsPlayer> createPlayers(final Set<UUID> playerIds, final GameConfig gameConfig) {
        return playerIds.stream()
                .map(playerId -> ThunderShipsPlayerFactory.createPlayer(playerId, gameConfig))
                .collect(Collectors.toSet());
    }

}
