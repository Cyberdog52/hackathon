package ch.zuehlke.challenge.bot.client;

import ch.zuehlke.challenge.bot.service.ShutDownService;
import ch.zuehlke.challenge.bot.util.ApplicationProperties;
import ch.zuehlke.common.JoinRequest;
import ch.zuehlke.common.MatchLobby;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchClient {

    private final RestTemplate hackathonRestTemplateClient;

    private final ApplicationProperties applicationProperties;

    private final ShutDownService shutDownService;

    public MatchLobby join(final String playerId, final String matchId) {
        final JoinRequest joinRequest = new JoinRequest(playerId);
        log.info("Joining game with request {}", joinRequest);

        // Improve: Handle exceptions
        final var responseEntity = hackathonRestTemplateClient
                .postForEntity(applicationProperties.getBackendWaitingBaseUrl() + "/" + matchId + "/join",
                        joinRequest,
                        MatchLobby.class
                );
        log.info("Received response: {}", responseEntity);
        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            final var match = responseEntity.getBody();
            log.info("Joined game with PlayerId: {}", match.getId());
            return match;
        } else {
            log.error("Could not join game. Will shutdown now...");
            shutDownService.shutDown();
            // Needed to return something even though exit(0) is called
            return null;
        }
    }


    public List<MatchLobby> getWaitingOpen() {
        return hackathonRestTemplateClient.exchange(applicationProperties.getBackendWaitingBaseUrl(),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<MatchLobby>>() {
                }).getBody();
    }

}
