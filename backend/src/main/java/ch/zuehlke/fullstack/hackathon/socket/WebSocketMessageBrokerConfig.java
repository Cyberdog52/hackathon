package ch.zuehlke.fullstack.hackathon.socket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketMessageBrokerConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        // Improve: configure a heartbeat to check if the bots are still connected
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Improve: use this to get updates from the bots
      registry.addEndpoint("/update").setAllowedOrigins("*").withSockJS();
      registry.addEndpoint("/update").setAllowedOrigins("*");
      registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
      registry.addEndpoint("/ws").setAllowedOrigins("*");

    }
}
