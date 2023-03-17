package ch.zuehlke.fullstack.hackathon.controller;

public record PlayResult(PlayResultType resultType) {

    public enum PlayResultType {
        SUCCESS,
        GAME_NOT_FOUND,
        PLAYER_NOT_FOUND,
        INVALID_ACTION
    }
}
