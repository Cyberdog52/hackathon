package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.common.Coordinate;
import ch.zuehlke.common.shared.event.playing.AttackEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MockGameVisitorNotificationService {

    @Autowired
    private final SimpMessagingTemplate template;

    @Scheduled(cron = "*/2 * * * * *")
    public void sendAction() {
        String gameId = "1d71d4f0-80a0-483c-8f30-9d73fbf7b331";
        String topic = MessageFormat.format("/topic/game/{0}/spectate", gameId);
        log.info(MessageFormat.format("Sending Mocked Attack Event to game spectarors topic:{0}", topic));
        Coordinate coordinate = new Coordinate(getRandomNumberUsingNextInt(0, 24), getRandomNumberUsingNextInt(0, 24));
        template.convertAndSend(
                topic,
                new AttackEvent(AttackEvent.AttackStatus.MISS, UUID.randomUUID(), coordinate)
        );
    }

    public int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
