package ch.zuehlke.challenge.bot.execution;

import org.springframework.stereotype.Service;

@Service
public interface GameExecutionHandler<T> {
    void execute(T payload);

    Action supports();

}
