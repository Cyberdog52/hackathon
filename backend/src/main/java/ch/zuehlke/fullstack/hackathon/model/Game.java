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

    private GameState state = new GameState();
    private final InternalGameState internalGameState = new InternalGameState();

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
                players.size() <= MAX_PLAYERS &&
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
        var attacker = internalGameState.attackersTurn();
        var playerId = players.get(attacker ? 0 : 1).id();
        var possibleActions = internalGameState.getPossibleActions();

        state.currentRequests().add(new PlayRequest(playerId, gameId, attacker, internalGameState.board(), possibleActions));
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
        var move = currentMoves.get(0);
        state.moves().add(move);
        internalGameState.playAction(move.action());
        state = new GameState(state.currentRequests(), state.moves(), internalGameState.board());

        if (internalGameState.isGameFinished()) {
            finishGame();
        } else {
            startRound();
        }
    }

    public Optional<PlayerId> getWinner() {
        if (status != GameStatus.FINISHED || state.moves().isEmpty() || internalGameState.isDraw()) {
            return Optional.empty();
        }
        return Optional.ofNullable(state.moves().get(state.moves().size() - 1).playerId());
    }
}
