package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.fullstack.hackathon.model.Game;
import ch.zuehlke.fullstack.hackathon.model.GameId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    // Improve: Instead of storing this in-memory, store it in a database
    private final List<Game> games;

    public GameService() {
        // Mock the first game already show something in the frontend
        games = new ArrayList<>();
        createGame();
    }

    public List<Game> getGames() {
        return games;
    }

    public Game createGame() {
        // Improve: Find a better way to create game ids
        Game game = new Game(new GameId(games.size()));
        games.add(game);
        return game;
    }
}
