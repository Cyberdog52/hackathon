package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.common.Player;
import ch.zuehlke.common.PlayerId;
import ch.zuehlke.common.PlayerName;
import ch.zuehlke.common.TournamentId;
import ch.zuehlke.fullstack.hackathon.controller.TournamentJoinResult;
import ch.zuehlke.fullstack.hackathon.controller.TournamentStartResult;
import ch.zuehlke.fullstack.hackathon.model.Game;
import ch.zuehlke.fullstack.hackathon.model.Tournament;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TournamentService {
    private final GameService gameService;
    // Improve: Instead of storing this in-memory, store it in a database
    private final List<Tournament> tournaments = new ArrayList<>();
    private static int counter = 0;

    public List<Tournament> getTournaments() {
        return tournaments;
    }

    public Tournament createTournament() {
        // Improve: Find a better way to create game ids
        counter += 1;
        var tournament = new Tournament(new TournamentId(counter));
        tournaments.add(tournament);
        return tournament;
    }


    public boolean deleteTournament(int tournamentId) {
        return tournaments.removeIf(t -> t.getTournamentId().value() == tournamentId);
    }

    public Optional<Tournament> getTournament(int tournamentId) {
        return tournaments.stream()
                .filter(t -> t.getTournamentId().value() == tournamentId)
                .findFirst();
    }

    public TournamentJoinResult join(int tournamentId, PlayerName name) {
        Optional<Tournament> tournament = getTournament(tournamentId);
        if (tournament.isEmpty()) {
            return new TournamentJoinResult(null, TournamentJoinResult.TournamentJoinResultType.TOURNAMENT_NOT_FOUND);
        }
        Player newPlayer = new Player(new PlayerId(), name);

        boolean success = tournament.get().addPlayer(newPlayer);
        if (!success) {
            return new TournamentJoinResult(null, TournamentJoinResult.TournamentJoinResultType.TOURNAMENT_FULL);
        }

        return new TournamentJoinResult(newPlayer.id(), TournamentJoinResult.TournamentJoinResultType.SUCCESS);
    }

    public TournamentStartResult startTournament(int tournamentId) {
        Optional<Tournament> optionalTournament = getTournament(tournamentId);
        if (optionalTournament.isEmpty()) {
            return new TournamentStartResult(TournamentStartResult.TournamentStartResultType.TOURNAMENT_NOT_FOUND);
        }

        Tournament tournament = optionalTournament.get();
        if (!tournament.canStartTournament()) {
            return new TournamentStartResult(TournamentStartResult.TournamentStartResultType.NOT_ENOUGH_PLAYERS);
        }

        tournament.startTournament();
        Game game = gameService.createGame();
        game.addPlayer(tournament.getPlayers().get(0));
        game.addPlayer(tournament.getPlayers().get(1));

        return new TournamentStartResult(TournamentStartResult.TournamentStartResultType.SUCCESS);
    }
}
