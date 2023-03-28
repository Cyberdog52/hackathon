package ch.zuehlke.common;

public record Move(String playerId, RequestId requestId, GameAction action) {
}
