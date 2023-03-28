package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.common.GameUpdate;
import ch.zuehlke.fullstack.hackathon.model.mapper.GameMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate template;
    private final GameService gameService;

    public void notifyGameStart(final UUID gameId) {
        this.gameService.getGame(gameId)
                .map(GameMapper::map)
                .ifPresent(gameDto -> template.convertAndSend("/topic/game/%s".formatted(gameId), new GameUpdate(gameDto)));
    }

//    private final GameService gameService;

//    public void notifyGameUpdate(GameId gameId) {
//        gameService.getGame(gameId.value())
//                .map(GameMapper::map)
//                .ifPresent(game -> template.convertAndSend("/topic/game/" + gameId.value(), new GameUpdate(game))
//                );
//    }
}
