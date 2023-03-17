package ch.zuehlke.fullstack.hackathon.controller;

public record StartResult(StartResultType resultType) {

    public enum StartResultType {
        SUCCESS,
        NOT_ENOUGH_PLAYERS,
        GAME_NOT_FOUND
    }
}
