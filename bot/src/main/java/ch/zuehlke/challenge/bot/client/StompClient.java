package ch.zuehlke.challenge.bot.client;

import ch.zuehlke.challenge.bot.service.GameService;
import ch.zuehlke.challenge.bot.service.ShutDownService;
import ch.zuehlke.challenge.bot.util.ApplicationProperties;
import ch.zuehlke.common.GameUpdate;
import ch.zuehlke.common.shared.event.EventType;
import ch.zuehlke.common.websocket.WebsocketDestination;
import jakarta.annotation.PreDestroy;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
// Adapted from tutorial at https://blog.dkwr.de/development/spring/spring-stomp-client/
public class StompClient implements StompSessionHandler {

    @NonNull
    private final ShutDownService shutDownService;

    private final ApplicationProperties applicationProperties;

    private StompSession stompSession;

    private StompSession.Subscription subscription;

    private final GameService gameService;

    @EventListener(value = ApplicationReadyEvent.class)
    public void connect() {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        try {
            String socketUrl = applicationProperties.getWebSocketUri();
            stompSession = stompClient.connectAsync(socketUrl, this).get();
        } catch (Exception e) {
            log.error("Connection failed.", e); // Improve: error handling.
            shutDownService.shutDown();
        }
    }

    public void subscribe(UUID id) {
        log.info("Subscribing to id: {}", id);
        String destination = String.format("%s/%s", WebsocketDestination.TOPIC_GAMES.getDestination(), id);
        this.subscription = stompSession.subscribe(destination, this);
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        log.info("Connection to STOMP server established");
        subscribe(applicationProperties.getGameId());
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        log.error("""
                Got an exception while handling a frame.
                Command: {}
                Headers: {}
                Payload: {}
                {}""", command, headers, payload, exception);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        log.error("Retrieved a transport error: {}", session);
        exception.printStackTrace();
        if (!session.isConnected()) {
            this.subscription = null;
            connect();
        }
    }

    @Override
    public Type getPayloadType(final StompHeaders headers) {
        return GameUpdate.class;
    }

    @Override
    public void handleFrame(final StompHeaders headers, final Object payload) {
        log.info("Got a new message {}", payload);

        List<String> eventTypes = headers.get("EventType");
        EventType eventType = EventType.valueOf(eventTypes.get(0));

        gameService.onGameUpdate(eventType, payload);
    }

    @PreDestroy
    void onShutDown() {
        if (this.subscription != null) {
            this.subscription.unsubscribe();
        }

        if (stompSession != null) {
            stompSession.disconnect();
        }
    }
}
