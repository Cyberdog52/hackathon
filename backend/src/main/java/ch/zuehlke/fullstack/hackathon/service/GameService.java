//package ch.zuehlke.fullstack.hackathon.service;
//
//import ch.zuehlke.common.*;
//import ch.zuehlke.fullstack.hackathon.controller.JoinResult;
//import ch.zuehlke.fullstack.hackathon.controller.JoinResult.JoinResultType;
//import ch.zuehlke.fullstack.hackathon.controller.PlayResult;
//import ch.zuehlke.fullstack.hackathon.controller.PlayResult.PlayResultType;
//import ch.zuehlke.fullstack.hackathon.controller.StartResult;
//import ch.zuehlke.fullstack.hackathon.model.Match;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class GameService {
//
//    // Improve: Instead of storing this in-memory, store it in a database
//    private final List<Match> games = new ArrayList<>();
//    private static int counter = 0;
//
//    public List<Match> getGames() {
//        return games;
//    }
//
//    public Match createGame() {
//        // Improve: Find a better way to create game ids
//        counter += 1;
//        Match game = new Match(new GameId(counter));
//        games.add(game);
//        return game;
//    }
//
//
//    public boolean deleteGame(int gameId) {
//        return games.removeIf(game -> game.getGameId().value() == gameId);
//    }
//
//    public Optional<Match> getGame(int gameId) {
//        return games.stream()
//                .filter(game -> game.getGameId().value() == gameId)
//                .findFirst();
//    }
//
//    public JoinResult join(int gameId, PlayerName name) {
//        Optional<Match> game = getGame(gameId);
//        if (game.isEmpty()) {
//            return new JoinResult(null, JoinResultType.GAME_NOT_FOUND);
//        }
//        Player newPlayer = new Player(new PlayerId(), name);
//
//        boolean success = game.get().addPlayer(newPlayer);
//        if (!success) {
//            return new JoinResult(null, JoinResultType.GAME_FULL);
//        }
//
//        return new JoinResult(newPlayer.id(), JoinResultType.SUCCESS);
//    }
//
//    public StartResult startGame(int gameId) {
//        Optional<Match> optionalGame = getGame(gameId);
//        if (optionalGame.isEmpty()) {
//            return new StartResult(StartResult.StartResultType.GAME_NOT_FOUND);
//        }
//
//        Match game = optionalGame.get();
//        if (!game.canStartGame()) {
//            return new StartResult(StartResult.StartResultType.NOT_ENOUGH_PLAYERS);
//        }
//
//        game.startGame();
//
//        return new StartResult(StartResult.StartResultType.SUCCESS);
//    }
//
//    public PlayResult play(Move move, GameId gameId) {
//        Optional<Match> optionalGame = getGame(gameId.value());
//        if (optionalGame.isEmpty()) {
//            return new PlayResult(PlayResultType.GAME_NOT_FOUND);
//        }
//
//        Match game = optionalGame.get();
//        if (!game.isMoveAllowed(move)) {
//            return new PlayResult(PlayResultType.INVALID_ACTION);
//        }
//
//        game.playMove(move);
//
//        return new PlayResult(PlayResultType.SUCCESS);
//    }
//}
