package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.common.*;
import ch.zuehlke.fullstack.hackathon.controller.JoinResult;
import ch.zuehlke.fullstack.hackathon.controller.JoinResult.JoinResultType;
import ch.zuehlke.fullstack.hackathon.controller.PlayResult;
import ch.zuehlke.fullstack.hackathon.controller.PlayResult.PlayResultType;
import ch.zuehlke.fullstack.hackathon.controller.StartResult;
import ch.zuehlke.fullstack.hackathon.model.Game;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GameService {

    // Improve: Instead of storing this in-memory, store it in a database
    private final List<Game> games = new ArrayList<>();

    public List<Game> getGames() {
        return games;
    }

    public Game createGame() {
        Game game = new Game(new GameId());
        games.add(game);
        return game;
    }


    public boolean deleteGame(String gameId) {
        return games.removeIf(game -> Objects.equals(game.getGameId().value(), gameId));
    }

    public Optional<Game> getGame(String gameId) {
        return games.stream()
                .filter(game -> Objects.equals(game.getGameId().value(), gameId))
                .findFirst();
    }

    public JoinResult join(String gameId, PlayerName name) {
        Optional<Game> game = getGame(gameId);
        if (game.isEmpty()) {
            return new JoinResult(null, null, JoinResultType.GAME_NOT_FOUND);
        }
        Player newPlayer = new Player(new PlayerId(), name, new PlayerToken(), new Board());

        boolean success = game.get().addPlayer(newPlayer);
        if (!success) {
            return new JoinResult(null, null, JoinResultType.GAME_FULL);
        }

        return new JoinResult(newPlayer.id(), newPlayer.token(), JoinResultType.SUCCESS);
    }

    public StartResult startGame(String gameId) {
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
        //if (!game.isMoveAllowed(move)) {
        //    return new PlayResult(PlayResultType.INVALID_ACTION);
        //}

        game.playMove(move);

        return new PlayResult(PlayResultType.SUCCESS);
    }
}
