package ch.zuehlke.challenge.bot.client;

import ch.zuehlke.challenge.bot.service.ShutDownService;
import ch.zuehlke.challenge.bot.util.ApplicationProperties;
import ch.zuehlke.common.JoinResponse;
import ch.zuehlke.common.PlayerCreateDto;
import ch.zuehlke.common.PlayerId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerClient {

    private final RestTemplate hackathonRestTemplateClient;

    private final ApplicationProperties applicationProperties;

    private final ShutDownService shutDownService;

    public PlayerId createPlayer(final String name, final String icon) {
        PlayerCreateDto playerCreateDto = new PlayerCreateDto(name, icon);

        // Improve: Handle exceptions
        ResponseEntity<String> responseEntity = hackathonRestTemplateClient
                .postForEntity(applicationProperties.getBackendCreatePlayerUrl(),
                        playerCreateDto,
                        String.class
                );
        log.info("Received response: {}", responseEntity);
        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            String playerId = responseEntity.getBody();
            log.info("Created player with PlayerId: {}", playerId);
            return new PlayerId(playerId);
        } else {
            log.error("Could not create player. Will shutdown now...");
            shutDownService.shutDown();
            // Needed to return something even though exit(0) is called
            return null;
        }

    }
}
