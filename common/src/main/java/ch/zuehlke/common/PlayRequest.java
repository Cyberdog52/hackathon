package ch.zuehlke.common;

public record PlayRequest(PlayerId playerId, RequestId requestId) {

    public PlayRequest(PlayerId playerId) {
        this(playerId, new RequestId());
    }
}
