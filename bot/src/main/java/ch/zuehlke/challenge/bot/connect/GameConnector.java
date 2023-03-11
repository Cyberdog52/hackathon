package ch.zuehlke.challenge.bot.connect;

import ch.zuehlke.challenge.bot.util.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static java.text.MessageFormat.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameConnector {

    private final RestTemplate hackathonRestTemplateClient;

    private final ApplicationProperties applicationProperties;

    @EventListener(ApplicationReadyEvent.class)
    public void join() {
        log.info("Joining game...");
        SignUpRequest signUpRequest = new SignUpRequest(applicationProperties.getName());
        ResponseEntity<SignUpResponse> signUpResponse = hackathonRestTemplateClient
                .postForEntity(applicationProperties.getBackendJoinUrl(),
                        signUpRequest,
                        SignUpResponse.class,
                        applicationProperties.getGameId()
                );
        log.info(format("Received response: {0}", signUpResponse));
    }


}
