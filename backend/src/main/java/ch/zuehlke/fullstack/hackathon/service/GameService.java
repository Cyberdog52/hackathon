package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.common.*;
import ch.zuehlke.fullstack.hackathon.controller.PlayResult;
import ch.zuehlke.fullstack.hackathon.controller.PlayResult.PlayResultType;
import ch.zuehlke.fullstack.hackathon.controller.StartResult;
import ch.zuehlke.common.Player;
import ch.zuehlke.fullstack.hackathon.model.Game;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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


    public boolean deleteGame(int gameId) {
        return games.removeIf(game -> game.getGameId().value() == gameId);
    }

    public Optional<Game> getGame(int gameId) {
        return games.stream()
                .filter(game -> game.getGameId().value() == gameId)
                .findFirst();
    }

    /*public JoinResult join(int gameId, PlayerName name) {
        Optional<Game> game = getGame(gameId);
        if (game.isEmpty()) {
            return new JoinResult(null, JoinResultType.GAME_NOT_FOUND);
        }
        Bot newPlayer = new Bot(name);

        boolean success = game.get().addPlayer(newPlayer);
        if (!success) {
            return new JoinResult(null, JoinResultType.GAME_FULL);
        }

        return new JoinResult(newPlayer.id(), JoinResultType.SUCCESS);
    }*/

    public StartResult startGame(int gameId) {
        Optional<Game> optionalGame = getGame(gameId);
        if (optionalGame.isEmpty()) {
            return new StartResult(StartResult.StartResultType.GAME_NOT_FOUND);
        }

        Game game = optionalGame.get();
        if (!game.canStartGame()) {
            return new StartResult(StartResult.StartResultType.NOT_ENOUGH_PLAYERS);
        }

        game.startGame();

        return new StartResult(StartResult.StartResultType.SUCCESS);
    }

    public PlayResult play(Move move, GameId gameId) {
        Optional<Game> optionalGame = getGame(gameId.value());
        if (optionalGame.isEmpty()) {
            return new PlayResult(PlayResultType.GAME_NOT_FOUND);
        }

        Game game = optionalGame.get();
        if (!game.isMoveAllowed(move)) {
            return new PlayResult(PlayResultType.INVALID_ACTION);
        }

        game.playMove(move);

        return new PlayResult(PlayResultType.SUCCESS);
    }

    private Map<String, Player> playersById = new HashMap<>();

    public ResponseEntity<RegisterResponse> register(String playerName) {
        Player player = new Player(playerName);
        RegisterResponse response = new RegisterResponse(player);
        playersById.put(player.id(), player);
        return ResponseEntity.ok(response);

    }
}
