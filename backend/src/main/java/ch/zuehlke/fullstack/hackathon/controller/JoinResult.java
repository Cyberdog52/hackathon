package ch.zuehlke.fullstack.hackathon.controller;

import ch.zuehlke.common.PlayerId;
import org.springframework.lang.Nullable;

public record JoinResult(@Nullable PlayerId playerId, JoinResultType resultType) {

    public enum JoinResultType {
        SUCCESS,
        GAME_FULL,
        GAME_NOT_FOUND
    }
}
