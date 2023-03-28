package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.common.Player;
import ch.zuehlke.common.PlayerNameResponse;
import ch.zuehlke.common.PlayerScoreResponse;
import ch.zuehlke.common.RegisterResponse;
import ch.zuehlke.fullstack.hackathon.controller.StartResult;
import ch.zuehlke.fullstack.hackathon.model.Game;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {

    private final Map<String, Player> playersById = new HashMap<>();

    // Improve: Instead of storing this in-memory, store it in a database
    private final List<Game> games = new ArrayList<>();
    private final Map<Player, Integer> scores = new HashMap<>();

    public List<Game> getGames() {
        return games;
    }

    public Game createGame(String firstPlayerId, String secondPlayerId) {

        Game game = new Game();
        // todo: throw Exception, if bot does not exist!
        game.addPlayer(playersById.get(firstPlayerId));
        game.addPlayer(playersById.get(secondPlayerId));

        games.add(game);
        return game;
    }

    public List<Player> getRegisteredPlayers() {
        return playersById.values().stream()
                .toList();
    }


    public boolean deleteGame(String gameId) {
        return games.removeIf(game -> Objects.equals(game.getGameId(), gameId));
    }

    public Optional<Game> getGame(String gameId) {
        return games.stream()
                .filter(game -> Objects.equals(game.getGameId(), gameId))
                .findFirst();
    }

   /* public JoinResult join(String gameId, PlayerName name) {
        Optional<Game> game = getGame(gameId);
        if (game.isEmpty()) {
            return new JoinResult(null, null, JoinResult.JoinResultType.GAME_NOT_FOUND);
        }
        Player newPlayer = new Player(new PlayerId(), name, new PlayerToken(), new Board());

        boolean success = game.get().addPlayer(newPlayer);
        if (!success) {
            return new JoinResult(null, null, JoinResult.JoinResultType.GAME_FULL);
        }

        return new JoinResult(newPlayer.getId(), newPlayer.getToken(), JoinResult.JoinResultType.SUCCESS);
    }*/

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

    public ResponseEntity<RegisterResponse> register(String playerName) {
        Player player = new Player(playerName);
        playersById.put(player.getId(), player);
        scores.put(player, 0);
        RegisterResponse response = new RegisterResponse(player);
        return ResponseEntity.ok(response);
    }

    public List<PlayerNameResponse> getActivePlayers() {
        List<PlayerNameResponse> playerNameResponses = new ArrayList<>();
        playersById.values().stream().forEach(player -> playerNameResponses.add(new PlayerNameResponse(player.getId(), player.getPlayerName())));
        return playerNameResponses;
    }

    public List<PlayerScoreResponse> getTop10() {
        return new ArrayList<>(); //TODO
    }
}
