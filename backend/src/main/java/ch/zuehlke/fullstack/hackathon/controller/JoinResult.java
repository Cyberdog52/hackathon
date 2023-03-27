package ch.zuehlke.fullstack.hackathon.controller;

import ch.zuehlke.common.PlayerId;
import ch.zuehlke.common.PlayerToken;
import org.springframework.lang.Nullable;

public record JoinResult(@Nullable PlayerId playerId, @Nullable PlayerToken playerToken, JoinResultType resultType) {

    public enum JoinResultType {
        SUCCESS,
        GAME_FULL,
        GAME_NOT_FOUND
    }
}
