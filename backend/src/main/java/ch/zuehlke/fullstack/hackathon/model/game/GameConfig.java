package ch.zuehlke.fullstack.hackathon.model.game;

import lombok.Builder;

@Builder
public record GameConfig(
        int mapWidth,
        int mapHeight,
        int maxNumberOfBoats
) {

}
