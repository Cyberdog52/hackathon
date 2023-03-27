package ch.zuehlke.common;

public record Player(PlayerId id, PlayerName name, PlayerToken token, Board board) {
}
