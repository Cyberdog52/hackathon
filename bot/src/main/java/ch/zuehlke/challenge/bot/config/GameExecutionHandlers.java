package ch.zuehlke.challenge.bot.config;

import ch.zuehlke.challenge.bot.execution.Action;
import ch.zuehlke.challenge.bot.execution.GameExecutionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Configuration
public class GameExecutionHandlers {

    @Bean
    public Map<Action, GameExecutionHandler> mapGameExecutionHandlers(
            final List<GameExecutionHandler> gameExecutionHandlers) {
        return gameExecutionHandlers.stream()
                .collect(toMap(handler -> handler.supports(), handler -> handler));
    }

}
