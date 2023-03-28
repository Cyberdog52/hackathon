package ch.zuehlke.challenge.bot.execution;

import ch.zuehlke.challenge.bot.client.GameClient;
import ch.zuehlke.common.shared.event.lobby.PlayerJoinEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinGameExecutionHandler implements GameExecutionHandler<PlayerJoinEvent> {

    @NonNull
    private final GameClient gameClient;

    @Override
    public void execute(PlayerJoinEvent playerJoinEvent) {
        // logic for joining game
        gameClient.joinLobby();
    }

    @Override
    public Action supports() {
        return Action.JOIN_GAME;
    }
}
