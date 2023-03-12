package ch.zuehlke.fullstack.hackathon.model;

import ch.zuehlke.common.GameDto;

public class GameMapper {

    public static GameDto map(Game game) {
        return GameDto.builder()
                .id(game.getGameId())
                .players(game.getPlayers())
                .status(game.getStatus())
                .state(game.getState())
                .build();
    }
}
