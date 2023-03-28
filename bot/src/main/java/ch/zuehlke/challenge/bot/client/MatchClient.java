package ch.zuehlke.challenge.bot.client;

import ch.zuehlke.challenge.bot.service.ShutDownService;
import ch.zuehlke.challenge.bot.util.ApplicationProperties;
import ch.zuehlke.common.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchClient {

    private final RestTemplate hackathonRestTemplateClient;

    private final ApplicationProperties applicationProperties;

    private final ShutDownService shutDownService;

    public Match join(final String playerId, final String matchId) {
        JoinRequest joinRequest = new JoinRequest(playerId);
        log.info("Joining game with request {}", joinRequest);

        // Improve: Handle exceptions
        ResponseEntity<Match> responseEntity = hackathonRestTemplateClient
                .postForEntity(applicationProperties.getBackendMatchBaseUrl() + "/" + matchId + "/join",
                        joinRequest,
                        Match.class
                );
        log.info("Received response: {}", responseEntity);
        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            Match match = responseEntity.getBody();
            log.info("Joined game with PlayerId: {}", match.id());
            return match;
        } else {
            log.error("Could not join game. Will shutdown now...");
            shutDownService.shutDown();
            // Needed to return something even though exit(0) is called
            return null;
        }
    }


    public List<Match> getWaitingOpen() {
        ResponseEntity<Match[]> responseEntity = hackathonRestTemplateClient
                .getForEntity(applicationProperties.getBackendWaitingBaseUrl() + "/open",
                        Match[].class
                );
        log.info("Received response: {}", responseEntity);
        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            Match[] matches = responseEntity.getBody();
            return Arrays.stream(matches).toList();
        } else {
            log.error("Could not join game. Will shutdown now...");
            shutDownService.shutDown();
            // Needed to return something even though exit(0) is called
            return null;
        }
    }

}
