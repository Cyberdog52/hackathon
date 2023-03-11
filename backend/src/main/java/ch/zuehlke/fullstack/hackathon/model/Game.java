package ch.zuehlke.fullstack.hackathon.model;

import ch.zuehlke.common.GameId;
import ch.zuehlke.common.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
// Improve: use different classes for DTO response objects and domain model objects
public class Game {
    private final GameId gameId;
    private final List<Player> players = new ArrayList<>();
    private GameState gameState = GameState.WAITING_FOR_PLAYERS;

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void startGame() {
        gameState = GameState.IN_PROGRESS;
    }

    public void finishGame() {
        gameState = GameState.FINISHED;
    }

    public void deleteGame() {
        gameState = GameState.DELETED;
    }

}
