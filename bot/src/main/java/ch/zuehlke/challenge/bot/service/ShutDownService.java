package ch.zuehlke.challenge.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.lang.System.exit;

@Service
@Slf4j
public class ShutDownService {

    public void shutDown() {
        log.info("Shutting down...");
        exit(0);
    }
}
