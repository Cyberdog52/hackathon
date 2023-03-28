package ch.zuehlke.fullstack.hackathon.controller;

public record TournamentStartResult(TournamentStartResultType resultType) {

    public enum TournamentStartResultType {
        SUCCESS,
        NOT_ENOUGH_PLAYERS,
        TOURNAMENT_NOT_FOUND
    }
}
