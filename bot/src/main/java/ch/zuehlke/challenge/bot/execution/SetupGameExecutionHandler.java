package ch.zuehlke.challenge.bot.execution;

import ch.zuehlke.challenge.bot.client.GameClient;
import ch.zuehlke.common.shared.event.setup.GameConfigEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SetupGameExecutionHandler implements GameExecutionHandler<GameConfigEvent> {

    @NonNull
    private final GameClient gameClient;

    @Override
    public void execute(final GameConfigEvent gameConfigEvent) {
    }

    @Override
    public Action supports() {
        return Action.SETUP_GAME;
    }
}
