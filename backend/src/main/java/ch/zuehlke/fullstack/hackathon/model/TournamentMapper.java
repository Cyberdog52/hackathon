package ch.zuehlke.fullstack.hackathon.model;

import ch.zuehlke.common.TournamentDto;

public class TournamentMapper {

    public static TournamentDto map(Tournament tournament) {
        return TournamentDto.builder()
                .id(tournament.getTournamentId())
                .players(tournament.getPlayers())
                .status(tournament.getStatus())
                .state(tournament.getState())
                .winner(tournament.getWinner().orElse(null))
                .scores(tournament.getScores())
                .build();
    }
}
