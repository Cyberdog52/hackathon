package ch.zuehlke.fullstack.hackathon.model;

import ch.zuehlke.common.GameId;
import ch.zuehlke.common.GameState;
import ch.zuehlke.common.GameStatus;
import ch.zuehlke.common.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class Game {

    public static final int MAX_PLAYERS = 2;

    private final GameId gameId;
    private final List<Player> players = new ArrayList<>();

    private GameStatus status = GameStatus.WAITING_FOR_PLAYERS;

    private GameState state = new GameState();

    public boolean addPlayer(Player player) {
        if (players.size() >= MAX_PLAYERS) {
            return false;
        }
        players.add(player);
        return true;
    }

    public void startGame() {
        status = GameStatus.IN_PROGRESS;
    }

    public void finishGame() {
        status = GameStatus.FINISHED;
    }

    public void deleteGame() {
        status = GameStatus.DELETED;
    }

}
