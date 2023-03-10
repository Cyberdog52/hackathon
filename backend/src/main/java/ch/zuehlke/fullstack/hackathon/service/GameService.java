package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.fullstack.hackathon.model.Game;
import ch.zuehlke.fullstack.hackathon.model.GameId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    // Improve: Instead of storing this in-memory, store it in a database
    private final List<Game> games = new ArrayList<>();
    private static int counter = 0;

    public List<Game> getGames() {
        return games;
    }

    public Game createGame() {
        // Improve: Find a better way to create game ids
        counter += 1;
        Game game = new Game(new GameId(counter));
        games.add(game);
        return game;
    }

    public void deleteGame(int gameId) {
        games.removeIf(game -> game.getGameId().id() == gameId);
    }
}
