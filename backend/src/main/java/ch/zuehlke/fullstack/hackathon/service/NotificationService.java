package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.common.Deal;
import ch.zuehlke.common.GameUpdate;
import ch.zuehlke.fullstack.hackathon.model.Game;
import ch.zuehlke.fullstack.hackathon.model.PlayerHand;
import ch.zuehlke.fullstack.hackathon.model.mapper.GameMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate template;

    public void notifyGameStart(final Game game) {
        final var gameDto = GameMapper.mapToRunning(game);
        template.convertAndSend("/topic/game/%s".formatted(game.getId()), new GameUpdate(gameDto));
    }

    public void notifyPlayerStart(final Game game, final PlayerHand playerHand){
        template.convertAndSendToUser(playerHand.player().id().toString(), "/topic/game/%s".formatted(game.getId().toString()), new Deal());

    }


    public void notifyPlayerJoined(final UUID playerId){
        template.convertAndSend("/topic/game/%s".formatted(playerId), playerId);
    }


    public void notifyPlayerTurn(final UUID playerId){

    }

//    private final GameService gameService;

//    public void notifyGameUpdate(GameId gameId) {
//        gameService.getGame(gameId.value())
//                .map(GameMapper::map)
//                .ifPresent(game -> template.convertAndSend("/topic/game/" + gameId.value(), new GameUpdate(game))
//                );
//    }
}
