package ch.zuehlke.fullstack.hackathon.model.mapper;

import ch.zuehlke.common.*;
import ch.zuehlke.fullstack.hackathon.model.Game;
import ch.zuehlke.fullstack.hackathon.model.Player;
import ch.zuehlke.fullstack.hackathon.model.PlayerHand;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameMapper {
    public static GameDto mapToRunning(final Game game) {
        return GameDto.builder()
                .id(game.getId())
                .players(game.getPlayers().stream()
                        .map(PlayerHand::player)
                        .map(GameMapper::map)
                        .toList())
                .status(GameStatus.IN_PROGRESS)
                .build();
    }

    public static GameDto mapToFinished(final Game game, final Player winner) {
        return GameDto.builder()
                .id(game.getId())
                .players(game.getPlayers().stream()
                        .map(PlayerHand::player)
                        .map(GameMapper::map)
                        .toList())
                .status(GameStatus.FINISHED)
                .winner(new PlayerId(winner.id().toString()))
                .build();
    }

    private static PlayerDto map(final Player player) {
        return PlayerDto.builder()
                .id(new PlayerId(player.id().toString()))
                .name(new PlayerName(player.name()))
                .icon(new PlayerIcon(player.icon()))
                .build();
    }
}
