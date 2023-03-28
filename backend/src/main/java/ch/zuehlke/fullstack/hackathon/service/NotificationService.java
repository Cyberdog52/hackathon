package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.common.GameId;
import ch.zuehlke.common.GameUpdate;
import ch.zuehlke.common.TournamentId;
import ch.zuehlke.common.TournamentUpdate;
import ch.zuehlke.fullstack.hackathon.model.GameMapper;
import ch.zuehlke.fullstack.hackathon.model.TournamentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate template;

    private final GameService gameService;
    private final TournamentService tournamentService;

    public void notifyGameUpdate(GameId gameId) {
        gameService.getGame(gameId.value())
                .map(GameMapper::map)
                .ifPresent(game -> template.convertAndSend("/topic/game/" + gameId.value(), new GameUpdate(game))
                );
    }

    public void notifyTournamentUpdate(TournamentId tournamentId) {
        tournamentService.getTournament(tournamentId.value())
                .map(TournamentMapper::map)
                .ifPresent(game -> template.convertAndSend("/topic/tournament/" + tournamentId.value(), new TournamentUpdate(game))
                );
    }
}
