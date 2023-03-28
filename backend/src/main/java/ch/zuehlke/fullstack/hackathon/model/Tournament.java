package ch.zuehlke.fullstack.hackathon.model;

import ch.zuehlke.common.Player;
import ch.zuehlke.common.TournamentId;
import ch.zuehlke.common.TournamentState;
import ch.zuehlke.common.TournamentStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class Tournament {

    public static final int MAX_PLAYERS = 20;
    public static final int MIN_PLAYERS = 2;

    private final TournamentId tournamentId;
    private final List<Player> players = new ArrayList<>();

    private TournamentStatus status = TournamentStatus.NOT_STARTED;

    private TournamentState state = new TournamentState();

    public boolean addPlayer(Player player) {
        if (players.size() >= MAX_PLAYERS) {
            return false;
        }
        players.add(player);
        return true;
    }

    public boolean canStartTournament() {
        return players.size() >= MIN_PLAYERS &&
                players.size() == MAX_PLAYERS &&
                status == TournamentStatus.NOT_STARTED;
    }

    public void startTournament() {
        if (!canStartTournament()) {
            return;
        }

        status = TournamentStatus.IN_PROGRESS;
        startRound();
    }

    private void startRound() {
//        state.currentRequests().add(new PlayRequest(players.get(internalGameState.getActionHistory().size() % 2).id(),
//                internalGameState.getPossibleActions()));
    }

    public void finishTournament() {
        status = TournamentStatus.FINISHED;
    }

    public void deleteTournament() {
        status = TournamentStatus.DELETED;
    }
}
