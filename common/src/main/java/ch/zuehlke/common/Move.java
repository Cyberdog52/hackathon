package ch.zuehlke.common;

public record Move(PlayerId playerId, RequestId requestId, GameId gameId, GameAction action) {

}
