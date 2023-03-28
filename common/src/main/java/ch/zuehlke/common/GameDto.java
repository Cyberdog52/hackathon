package ch.zuehlke.common;

import lombok.Builder;

import java.util.List;

@Builder
public record GameDto(String id, List<Player> players, List<Board> boards, GameStatus status, GameState state, List<String> winner) {
}
