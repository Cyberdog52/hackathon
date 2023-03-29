package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.common.Coordinate;
import ch.zuehlke.common.GamePlayingAction;
import ch.zuehlke.common.shared.event.GameEndEvent;
import ch.zuehlke.common.shared.event.GameStartPlayingEvent;
import ch.zuehlke.common.shared.event.playing.AttackEvent;
import ch.zuehlke.common.shared.event.playing.TakeTurnEvent;
import ch.zuehlke.common.shared.event.setup.PlaceBoatEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
public class MockGameVisitorNotificationService {

    private final SimpMessagingTemplate template;

    private final List<Record> gameEvents = getGameEvents();

    private int eventIndex = 0;

    public MockGameVisitorNotificationService(@Autowired
                                               final SimpMessagingTemplate template) {
        this.template = template;

    }

//    @Scheduled(cron = "*/1 * * * * *")
    public void sendAction() {
        eventIndex = eventIndex % gameEvents.size();

        String gameId = "1d71d4f0-80a0-483c-8f30-9d73fbf7b331";
        String topic = MessageFormat.format("/topic/game/{0}/spectate", gameId);
        log.info(MessageFormat.format("Sending Mocked Attack Event to game spectarors topic:{0}", topic));
        template.convertAndSend(
                topic,
                getNextGameEvent()
        );
    }

    public Record getNextGameEvent() {
        final Record event = gameEvents.get(eventIndex);
        eventIndex++;
        return event;
    }

    public static List<Record> getGameEvents() {
        UUID gameId = UUID.fromString("1d71d4f0-80a0-483c-8f30-9d73fbf7b331");

        Coordinate p1Miss1 = new Coordinate(getRandomNumberUsingNextInt(0, 4), getRandomNumberUsingNextInt(0, 4));
        Coordinate p1Miss2 = new Coordinate(getRandomNumberUsingNextInt(0, 4), getRandomNumberUsingNextInt(21, 24));
        Coordinate p1Miss3 = new Coordinate(getRandomNumberUsingNextInt(21, 24), getRandomNumberUsingNextInt(0, 4));
        Coordinate p1Miss4 = new Coordinate(getRandomNumberUsingNextInt(21, 24), getRandomNumberUsingNextInt(21, 24));

        Coordinate p1Boat1Coordinate = new Coordinate(getRandomNumberUsingNextInt(5, 20), getRandomNumberUsingNextInt(5, 20));
        Coordinate p1Boat2Coordinate = new Coordinate(getRandomNumberUsingNextInt(5, 20), getRandomNumberUsingNextInt(5, 20));
        Coordinate p1Boat3Coordinate = new Coordinate(getRandomNumberUsingNextInt(5, 20), getRandomNumberUsingNextInt(5, 20));
        Coordinate p1Boat4Coordinate = new Coordinate(getRandomNumberUsingNextInt(5, 20), getRandomNumberUsingNextInt(5, 20));
        Coordinate p1Boat5Coordinate = new Coordinate(getRandomNumberUsingNextInt(5, 20), getRandomNumberUsingNextInt(5, 20));

        Coordinate p2Miss1 = new Coordinate(getRandomNumberUsingNextInt(0, 4), getRandomNumberUsingNextInt(0, 4));
        Coordinate p2Miss2 = new Coordinate(getRandomNumberUsingNextInt(0, 4), getRandomNumberUsingNextInt(21, 24));
        Coordinate p2Miss3 = new Coordinate(getRandomNumberUsingNextInt(21, 24), getRandomNumberUsingNextInt(0, 4));
        Coordinate p2Miss4 = new Coordinate(getRandomNumberUsingNextInt(21, 24), getRandomNumberUsingNextInt(21, 24));

        Coordinate p2Boat1Coordinate = new Coordinate(getRandomNumberUsingNextInt(5, 20), getRandomNumberUsingNextInt(5, 20));
        Coordinate p2Boat2Coordinate = new Coordinate(getRandomNumberUsingNextInt(5, 20), getRandomNumberUsingNextInt(5, 20));
        Coordinate p2Boat3Coordinate = new Coordinate(getRandomNumberUsingNextInt(5, 20), getRandomNumberUsingNextInt(5, 20));
        Coordinate p2Boat4Coordinate = new Coordinate(getRandomNumberUsingNextInt(5, 20), getRandomNumberUsingNextInt(5, 20));
        Coordinate p2Boat5Coordinate = new Coordinate(getRandomNumberUsingNextInt(5, 20), getRandomNumberUsingNextInt(5, 20));

        UUID player1Id = UUID.fromString("1bcdebdc-8e97-4a6d-a0d8-c3f1cc0853f7");
        UUID player2Id = UUID.fromString("803b89be-deb1-427f-a582-b85739d6f4ec");

        return List.of(
                // PLACE BOATS
                new PlaceBoatEvent(player1Id, p1Boat1Coordinate, true, gameId),
                new PlaceBoatEvent(player1Id, p1Boat2Coordinate, true, gameId),
                new PlaceBoatEvent(player1Id, p1Boat3Coordinate, true, gameId),
                new PlaceBoatEvent(player1Id, p1Boat4Coordinate, true, gameId),
                new PlaceBoatEvent(player1Id, p1Boat5Coordinate, true, gameId),
                new PlaceBoatEvent(player2Id, p2Boat1Coordinate, true, gameId),
                new PlaceBoatEvent(player2Id, p2Boat2Coordinate, true, gameId),
                new PlaceBoatEvent(player2Id, p2Boat3Coordinate, true, gameId),
                new PlaceBoatEvent(player2Id, p2Boat4Coordinate, true, gameId),
                new PlaceBoatEvent(player2Id, p2Boat5Coordinate, true, gameId),

                // START GAME
                new GameStartPlayingEvent(List.of(player1Id, player2Id), gameId),

                // MISSING
                new TakeTurnEvent(player1Id, List.of(GamePlayingAction.ATTACK), gameId),
                new AttackEvent(AttackEvent.AttackStatus.MISS, player1Id, p1Miss1, gameId),
                new TakeTurnEvent(player2Id, List.of(GamePlayingAction.ATTACK), gameId),
                new AttackEvent(AttackEvent.AttackStatus.MISS, player2Id, p2Miss1, gameId),
                new TakeTurnEvent(player1Id, List.of(GamePlayingAction.ATTACK), gameId),
                new AttackEvent(AttackEvent.AttackStatus.MISS, player1Id, p1Miss2, gameId),
                new TakeTurnEvent(player2Id, List.of(GamePlayingAction.ATTACK), gameId),
                new AttackEvent(AttackEvent.AttackStatus.MISS, player2Id, p2Miss2, gameId),
                new TakeTurnEvent(player1Id, List.of(GamePlayingAction.ATTACK), gameId),
                new AttackEvent(AttackEvent.AttackStatus.MISS, player1Id, p1Miss3, gameId),
                new TakeTurnEvent(player2Id, List.of(GamePlayingAction.ATTACK), gameId),
                new AttackEvent(AttackEvent.AttackStatus.MISS, player2Id, p2Miss3, gameId),
                new TakeTurnEvent(player1Id, List.of(GamePlayingAction.ATTACK), gameId),
                new AttackEvent(AttackEvent.AttackStatus.MISS, player1Id, p1Miss4, gameId),
                new TakeTurnEvent(player2Id, List.of(GamePlayingAction.ATTACK), gameId),
                new AttackEvent(AttackEvent.AttackStatus.MISS, player2Id, p2Miss4, gameId),

                // HITTING
                new TakeTurnEvent(player1Id, List.of(GamePlayingAction.ATTACK), gameId),
                new AttackEvent(AttackEvent.AttackStatus.HIT, player1Id, p2Boat1Coordinate, gameId),
                new TakeTurnEvent(player2Id, List.of(GamePlayingAction.ATTACK), gameId),
                new AttackEvent(AttackEvent.AttackStatus.HIT, player2Id, p1Boat1Coordinate, gameId),
                new TakeTurnEvent(player1Id, List.of(GamePlayingAction.ATTACK), gameId),
                new AttackEvent(AttackEvent.AttackStatus.HIT, player1Id, p2Boat2Coordinate, gameId),
                new TakeTurnEvent(player2Id, List.of(GamePlayingAction.ATTACK), gameId),
                new AttackEvent(AttackEvent.AttackStatus.HIT, player2Id, p1Boat2Coordinate, gameId),
                new TakeTurnEvent(player1Id, List.of(GamePlayingAction.ATTACK), gameId),
                new AttackEvent(AttackEvent.AttackStatus.HIT, player1Id, p2Boat3Coordinate, gameId),
                new TakeTurnEvent(player2Id, List.of(GamePlayingAction.ATTACK), gameId),
                new AttackEvent(AttackEvent.AttackStatus.HIT, player2Id, p1Boat3Coordinate, gameId),
                new TakeTurnEvent(player1Id, List.of(GamePlayingAction.ATTACK), gameId),
                new AttackEvent(AttackEvent.AttackStatus.HIT, player1Id, p2Boat4Coordinate, gameId),
                new TakeTurnEvent(player2Id, List.of(GamePlayingAction.ATTACK), gameId),
                new AttackEvent(AttackEvent.AttackStatus.HIT, player2Id, p1Boat4Coordinate, gameId),
                new TakeTurnEvent(player1Id, List.of(GamePlayingAction.ATTACK), gameId),
                new AttackEvent(AttackEvent.AttackStatus.HIT, player1Id, p2Boat5Coordinate, gameId),

                // GAME END
                new GameEndEvent(player1Id, gameId)
        );
    }

    public static int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
