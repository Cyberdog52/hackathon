package ch.zuehlke.fullstack.hackathon.model.mapper;

import ch.zuehlke.common.*;
import ch.zuehlke.fullstack.hackathon.model.Game;
import ch.zuehlke.fullstack.hackathon.model.Player;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameMapper {

    public static GameDto map(final Game game) {
        return GameDto.builder()
                .id(game.getId())
                .players(game.getPlayers().stream().map(GameMapper::map).toList())
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
