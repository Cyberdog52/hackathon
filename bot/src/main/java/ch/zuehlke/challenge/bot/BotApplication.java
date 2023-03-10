package ch.zuehlke.challenge.bot;

import ch.zuehlke.challenge.bot.io.CommandLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class BotApplication implements CommandLineRunner {

    private final CommandLineService commandLineService;

    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);
    }

    @Override
    public void run(String... args) {
        commandLineService.setup();
    }
}
