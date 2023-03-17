package ch.zuehlke.fullstack.hackathon.model;

import ch.zuehlke.common.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public class Game {

    public static final int MAX_PLAYERS = 2;
    public static final int MIN_PLAYERS = 2;

    private final GameId gameId;
    private final List<Player> players = new ArrayList<>();

    private GameStatus status = GameStatus.NOT_STARTED;

    private final GameState state = new GameState();

    // moves is not exposed to the GameDto to avoid cheating
    private List<Move> currentMoves;

    public boolean addPlayer(Player player) {
        if (players.size() >= MAX_PLAYERS) {
            return false;
        }
        players.add(player);
        return true;
    }

    public boolean canStartGame() {
        return players.size() >= MIN_PLAYERS &&
                players.size() == MAX_PLAYERS &&
                status == GameStatus.NOT_STARTED;
    }

    public void startGame() {
        if (!canStartGame()) {
            return;
        }

        status = GameStatus.IN_PROGRESS;
        startRound();
    }

    private void startRound() {
        currentMoves = new ArrayList<>();
        players.forEach(player -> state.currentRequests().add(new PlayRequest(player.id())));
    }

    public void finishGame() {
        status = GameStatus.FINISHED;
    }

    public void deleteGame() {
        status = GameStatus.DELETED;
    }

    public boolean isMoveAllowed(Move move) {
        return status == GameStatus.IN_PROGRESS &&
                state.currentRequests().stream()
                        .anyMatch(request -> request.playerId().equals(move.playerId()) && request.requestId().equals(move.requestId()));
    }

    public void playMove(Move move) {
        if (!isMoveAllowed(move)) {
            return;
        }

        state.currentRequests().removeIf(request -> request.playerId().equals(move.playerId()));
        currentMoves.add(move);

        if (state.currentRequests().isEmpty()) {
            finishRound();
        }
    }

    private void finishRound() {
        Round currentRound = new Round(currentMoves.get(0), currentMoves.get(1));
        state.rounds().add(currentRound);

        // Improve: Do multiple rounds
        finishGame();
    }

    public Optional<PlayerId> getWinner() {
        if (status != GameStatus.FINISHED || state.rounds().isEmpty()) {
            return Optional.empty();
        }
        // Improve: Handle multiple rounds
        return Optional.ofNullable(state.rounds().get(0).winner());
    }
}
