package ch.zuehlke.fullstack.hackathon.model;

import ch.zuehlke.common.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
@Getter
public class Tournament {

    public static final int MAX_PLAYERS = 20;
    public static final int MIN_PLAYERS = 2;

    private final TournamentId tournamentId;
    private final List<Player> players = new ArrayList<>();

    private TournamentStatus status = TournamentStatus.NOT_STARTED;

    private TournamentState state = new TournamentState();
    private List<Score> scores = new ArrayList<>();

    public boolean addPlayer(Player player) {
        if (players.size() >= MAX_PLAYERS) {
            return false;
        }
        players.add(player);
        return true;
    }

    public boolean canStartTournament() {
        return players.size() >= MIN_PLAYERS &&
                players.size() <= MAX_PLAYERS &&
                status == TournamentStatus.NOT_STARTED;
    }

    public void startTournament() {
        if (!canStartTournament()) {
            return;
        }

        status = TournamentStatus.IN_PROGRESS;
    }

    public void finishTournament() {
        status = TournamentStatus.FINISHED;
    }

    public void deleteTournament() {
        status = TournamentStatus.DELETED;
    }

    public void updateFromGames(List<Game> games) {
        var gamesBelongingToTournament = games.stream()
                .filter(g -> state.games().contains(g.getGameId()))
                .toList();

        var allGamesFinished = gamesBelongingToTournament.stream()
                .map(Game::getStatus)
                .allMatch(s -> s == GameStatus.FINISHED);

        if (allGamesFinished) {
            finishTournament();
        }

        var finishedGames = gamesBelongingToTournament.stream()
                .filter(g -> g.getStatus() == GameStatus.FINISHED)
                .toList();

        var multiMap = new HashMap<PlayerId, List<Game>>();
        for (var game : finishedGames) {
            for (var player : game.getPlayers()) {
                multiMap.computeIfAbsent(player.id(), k -> new ArrayList<>())
                        .add(game);
            }
        }

        scores = multiMap.entrySet().stream()
                .map(e -> new Score(e.getKey(), calculateScore(e.getKey(), e.getValue())))
                .sorted(Comparator.comparing(Score::score).reversed())
                .toList();
    }

    private static int calculateScore(PlayerId playerId, List<Game> games) {
        int score = 0;

        for (Game game : games) {
            Optional<PlayerId> winner = game.getWinner();
            if (winner.isEmpty()) {
                score += 1;
            } else if (winner.get().equals(playerId)) {
                score += 3;
            }
        }

        return score;
    }

    public Optional<PlayerId> getWinner() {
        if (status != TournamentStatus.FINISHED || scores.isEmpty()) {
            return Optional.empty();
        }

        if (scores.size() >= 2 && scores.get(0).score() == scores.get(1).score()) {
            return Optional.empty();
        }

        return Optional.of(scores.get(0).playerId());
    }

    public List<Score> getScores() {
        return scores;
    }
}
