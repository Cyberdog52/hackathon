package ch.zuehlke.challenge.bot.client;

import ch.zuehlke.challenge.bot.service.GameService;
import ch.zuehlke.challenge.bot.util.ApplicationProperties;
import ch.zuehlke.common.GameUpdate;
import jakarta.annotation.PreDestroy;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.WebSocketContainer;
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

import java.awt.*;
import java.lang.reflect.Type;

@Slf4j
@Controller
@RequiredArgsConstructor
// Adapted from tutorial at https://blog.dkwr.de/development/spring/spring-stomp-client/
public class StompClient implements StompSessionHandler {

    private final ApplicationProperties applicationProperties;

    private StompSession stompSession;

    private StompSession.Subscription subscription;

    private final GameService gameService;

    @EventListener(value = ApplicationReadyEvent.class)
    public void connect() {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.setDefaultMaxTextMessageBufferSize(1024 * 1024 * 100);
        container.setDefaultMaxBinaryMessageBufferSize(1024 * 1024 * 100);
        WebSocketClient client = new StandardWebSocketClient(container);
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setInboundMessageSizeLimit(1024 * 1024 * 100);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        try {
            String socketUrl = applicationProperties.getWebSocketUri();
            stompSession = stompClient.connectAsync(socketUrl, this).get();
        } catch (Exception e) {
            log.error("Connection failed.", e); // Improve: error handling.
        }
    }

    private void subscribe(Integer gameId) {
        log.info("Subscribing to id: {}", gameId);
        this.subscription = stompSession.subscribe("/topic/game/" + gameId, this);
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
    public Type getPayloadType(StompHeaders headers) {
        return GameUpdate.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        log.info("Got a new message {}", payload);
        GameUpdate gameUpdate = (GameUpdate) payload;
        gameService.onGameUpdate(gameUpdate);
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
