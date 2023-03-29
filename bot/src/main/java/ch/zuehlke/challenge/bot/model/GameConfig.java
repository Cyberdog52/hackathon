package ch.zuehlke.challenge.bot.model;

import lombok.Builder;

@Builder
public record GameConfig(
        int mapHeight,
        int mapWidth,
        int numberOfBoats
) {

}
