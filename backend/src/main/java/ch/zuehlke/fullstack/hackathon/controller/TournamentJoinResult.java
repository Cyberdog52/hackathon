package ch.zuehlke.fullstack.hackathon.controller;

import ch.zuehlke.common.PlayerId;
import org.springframework.lang.Nullable;

public record TournamentJoinResult(@Nullable PlayerId playerId, TournamentJoinResultType resultType) {

    public enum TournamentJoinResultType {
        SUCCESS,
        TOURNAMENT_FULL,
        TOURNAMENT_NOT_FOUND
    }
}
