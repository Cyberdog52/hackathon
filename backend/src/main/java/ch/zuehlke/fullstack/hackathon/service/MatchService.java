package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.fullstack.hackathon.model.Match;
import ch.zuehlke.fullstack.hackathon.model.MatchLobby;
import ch.zuehlke.fullstack.hackathon.model.exception.MatchStartException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchService {

    // Improve: Instead of storing this in-memory, store it in a database
    private final List<Match> matches = new ArrayList<>();
    private final List<MatchLobby> lobbies = new ArrayList<>();

    private final GameService gameService;

    public List<Match> getMatches() {
        return matches;
    }

    public MatchLobby createMatch() {
        final var lobby = new MatchLobby(UUID.randomUUID());
        this.lobbies.add(lobby);
        return lobby;
    }

    public Match startMatch(final MatchLobby lobby) throws MatchStartException {
        final Match match;
        try {
            match = Match.fromLobby(lobby);
            this.matches.add(match);
        } catch (MatchStartException e) {
            log.error("Unable to start match", e);
            throw e;
        }
        this.gameService.startNewGame(match.getPlayers());
        return match;
    }

    public boolean deleteMatch(final UUID matchId) {
        return matches.removeIf(match -> match.getId() == matchId);
    }

    public List<MatchLobby> getWaitingMatches() {
        return this.lobbies;
    }

    public MatchLobby findLobby(final String matchId) throws MatchStartException {
        final var uuId = UUID.fromString(matchId);
        return this.getLobby(uuId)
                .orElseThrow(() -> new MatchStartException("Unable to start match %s because it does not exist".formatted(matchId)));
    }

    public Optional<MatchLobby> getLobby(final UUID matchId) {
        return this.lobbies.stream()
                .filter(item -> item.getId().equals(matchId))
                .findFirst();
    }

//    public Optional<Match> getGame(int gameId) {
//        return matches.stream()
//                .filter(game -> game.getGameId().rank() == gameId)
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
//        Optional<Match> optionalGame = getGame(gameId.rank());
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
}
