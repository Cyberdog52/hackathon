package ch.zuehlke.challenge.bot.execution;

import ch.zuehlke.challenge.bot.service.GameService;
import ch.zuehlke.common.shared.event.playing.TakeTurnEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TakeTurnExecutionHandler implements GameExecutionHandler<TakeTurnEvent> {

    @NonNull
    private final GameService gameService;

    @Override
    public void execute(final TakeTurnEvent takeTurnEvent) {
    }

    @Override
    public Action supports() {
        return Action.TAKE_TURN;
    }
}
